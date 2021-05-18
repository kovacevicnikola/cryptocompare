package com.magus.cryptocompare.ui.main;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Path;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.api.CryptoCompareService;
import com.magus.cryptocompare.api.schemas.Coin;
import com.magus.cryptocompare.api.schemas.PriceAndVolumeData;
import com.magus.cryptocompare.api.schemas.ResponseModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {
    private static float CHART_HEIGHT_DP = 300f;
    private CryptoCompareService service;
    private String apiKey;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        apiKey = getApplication().getString(R.string.crypto_compare_api_key);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(new Cache(httpCacheDirectory, cacheSize));
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        httpClient.readTimeout(5, TimeUnit.SECONDS);
        service = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(getApplication().getString(R.string.base_url))
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(CryptoCompareService.class);
    }

    Single<List<Coin>> getCoins() {
        return Single.create(emitter -> {
            Response<ResponseModel> response = service.getCryptoCoinList(true, apiKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                List<Coin> coins = new ArrayList<>();
                for (Map.Entry<String, Object> entry : response.body().getData().getAdditionalProperties().entrySet()) {
                    try {
                        coins.add(new Coin((LinkedHashMap<String, String>) entry.getValue()));
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                }
                emitter.onSuccess(coins);
            } else {
                emitter.onSuccess(new ArrayList<>());
            }
        });
    }

    public Single<Path> getGraphPath(CryptoDataType type, String valueFrom, String valueTo, int limit) {
        return Single.create(emitter -> {
            Resources r = getApplication().getResources();
            Response<ResponseModel> response = null;
            switch (type) {
                case DAILY:
                    response = service.getDataByDay(apiKey, limit, valueFrom, valueTo).execute();

                    break;
                case HOURLY:
                    response = service.getDataByHour(apiKey, limit, valueFrom, valueTo).execute();
                    break;

                case MINUTE:
                    response = service.getDataByMinute(apiKey, limit, valueFrom, valueTo).execute();

                    break;
            }
            if (response.isSuccessful() && response.body() != null) {
                Path path = new Path();
                float chartHeightPx = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        CHART_HEIGHT_DP,
                        r.getDisplayMetrics()
                );
                float chartWidthPx = r.getDisplayMetrics().widthPixels;
                Double maxHigh = Double.MIN_NORMAL;
                Integer maxTime = Integer.MIN_VALUE;
                Double minHigh = Double.MAX_VALUE;
                Integer minTime = Integer.MAX_VALUE;
                List<PriceAndVolumeData> priceAndVolumeList = response.body().getData().getData();
                for (PriceAndVolumeData priceAndVolumeData : priceAndVolumeList) {
                    if (priceAndVolumeData.getHigh() > maxHigh)
                        maxHigh = priceAndVolumeData.getHigh();
                    else if (priceAndVolumeData.getHigh() < minHigh)
                        minHigh = priceAndVolumeData.getHigh();
                    if (priceAndVolumeData.getTime() > maxTime)
                        maxTime = priceAndVolumeData.getTime();
                    else if (priceAndVolumeData.getTime() < minTime)
                        minTime = priceAndVolumeData.getTime();
                }
                int ct = 0;
                for (PriceAndVolumeData priceAndVolumeData : priceAndVolumeList) {
                    if (ct++ != 0)
                        path.moveTo(0, priceAndVolumeData.getHigh().floatValue() / maxHigh.floatValue() * chartHeightPx);
                    else
                        path.lineTo(priceAndVolumeData.getTime() / maxTime.floatValue() * chartWidthPx,
                                priceAndVolumeData.getHigh().floatValue() / maxHigh.floatValue() * chartHeightPx);
                }
                emitter.onSuccess(path);

            } else {
                //todo
            }


        });
    }

    public Single<Response<LinkedHashMap<String, String>>> getCoinExchangeRate(String symbolFrom, String[] symbolsTo) {
        return service.getCoinExchangeModel(apiKey, symbolFrom, symbolsTo);
    }

    public enum CryptoDataType {
        DAILY, HOURLY, MINUTE
    }


}