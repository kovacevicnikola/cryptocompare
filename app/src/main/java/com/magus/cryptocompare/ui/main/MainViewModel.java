package com.magus.cryptocompare.ui.main;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.api.CryptoCompareService;
import com.magus.cryptocompare.api.schemas.CoinSchema;
import com.magus.cryptocompare.api.schemas.PriceAndVolumeSchema;
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
    private Paint paintDaily;
    private Paint paintHourly;
    private Paint paintMinute;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        generatePaint();
        apiKey = getApplication().getString(R.string.crypto_compare_api_key);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
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

    private void generatePaint() {
        paintDaily = new Paint();
        paintDaily.setAntiAlias(true);
        paintDaily.setStrokeWidth(3);
        paintDaily.setColor(Color.GREEN);
        paintDaily.setStyle(Paint.Style.STROKE);
        paintHourly = new Paint();
        paintHourly.setAntiAlias(true);
        paintHourly.setStrokeWidth(3);
        paintHourly.setColor(Color.YELLOW);
        paintHourly.setStyle(Paint.Style.STROKE);
        paintMinute = new Paint();
        paintMinute.setAntiAlias(true);
        paintMinute.setStrokeWidth(3);
        paintMinute.setColor(Color.RED);
        paintMinute.setStyle(Paint.Style.STROKE);
    }

    public Paint getPaint(CryptoDataType data) {
        switch (data) {
            case DAILY:
                return paintDaily;
            case BYMINUTE:
                return paintMinute;
            case HOURLY:
                return paintHourly;
        }
        return null;

    }

    Single<List<CoinSchema>> getCoins() {
        return Single.create(emitter -> {
            Response<ResponseModel> response = service.getCryptoCoinList(true, apiKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                List<CoinSchema> coins = new ArrayList<>();
                for (Map.Entry<String, Object> entry : response.body().getData().getAdditionalProperties().entrySet()) {
                    try {
                        coins.add(new CoinSchema((LinkedHashMap<String, String>) entry.getValue()));
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

                case BYMINUTE:
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
                Long maxTime = Long.MIN_VALUE;
                Double minHigh = Double.MAX_VALUE;
                Long minTime = Long.MAX_VALUE;
                List<PriceAndVolumeSchema> priceAndVolumeList = response.body().getData().getData();
                for (PriceAndVolumeSchema priceAndVolumeSchema : priceAndVolumeList) {
                    if (priceAndVolumeSchema.getHigh() > maxHigh)
                        maxHigh = priceAndVolumeSchema.getHigh();
                    if (priceAndVolumeSchema.getHigh() < minHigh)
                        minHigh = priceAndVolumeSchema.getHigh();
                    if (priceAndVolumeSchema.getTime() > maxTime)
                        maxTime = priceAndVolumeSchema.getTime();
                    if (priceAndVolumeSchema.getTime() < minTime)
                        minTime = priceAndVolumeSchema.getTime();
                }
                boolean initial = true;
                Timber.d("maxHigh %f, maxTime %d, minHigh %f, minTime %d", maxHigh, maxTime, minHigh, minTime);
                Timber.d("chart width %f, chart height %f", chartHeightPx, chartHeightPx);
                for (PriceAndVolumeSchema priceAndVolumeSchema : priceAndVolumeList) {
                    float y = (priceAndVolumeSchema.getHigh().floatValue() - minHigh.floatValue()) / (maxHigh.floatValue() - minHigh.floatValue()) * chartHeightPx;
                    float x = (priceAndVolumeSchema.getTime().floatValue() - minTime) / (maxTime.floatValue() - minTime) * chartWidthPx;
                    if (initial) {
                        path.moveTo(0, y);
                        initial = false;
                    } else path.lineTo(x, y);
                    Timber.d("x %f, y %f", x, y);
                }
                emitter.onSuccess(path);

            } else {
                emitter.onError(new Throwable("No data for specified coin!"));
            }


        });
    }
    public Single<Response<LinkedHashMap<String, String>>> getCoinExchangeRate(String symbolFrom, String[] symbolsTo) {
        return service.getCoinExchangeModel(apiKey, symbolFrom, symbolsTo);
    }

    public enum CryptoDataType {
        DAILY, HOURLY, BYMINUTE
    }


}