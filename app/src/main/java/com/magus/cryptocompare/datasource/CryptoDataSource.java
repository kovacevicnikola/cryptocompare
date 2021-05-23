package com.magus.cryptocompare.datasource;

import android.app.Application;

import androidx.room.Room;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.datasource.api.CryptoCompareService;
import com.magus.cryptocompare.datasource.api.schemas.PriceAndVolumeSchema;
import com.magus.cryptocompare.datasource.api.schemas.ResponseModel;
import com.magus.cryptocompare.datasource.database.AppDatabase;
import com.magus.cryptocompare.datasource.database.CoinEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

public class CryptoDataSource {
    AppDatabase appDatabase;
    String apiKey;
    CryptoCompareService service;

    public CryptoDataSource(Application application) {
        appDatabase = Room.databaseBuilder(application, AppDatabase.class, "Crypto-db").build();
        apiKey = application.getString(R.string.crypto_compare_api_key);
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
                .baseUrl(application.getString(R.string.base_url))
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(CryptoCompareService.class);
    }


    public Single<List<CoinEntity>> getCryptoCoinList() {
        return Single.create(emitter -> {
            List<CoinEntity> coins;
            Response<ResponseModel> response = service.getCryptoCoinList(false, apiKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                coins = new ArrayList<>();
                for (Map.Entry<String, Object> entry : response.body().getData().getAdditionalProperties().entrySet()) {
                    try {
                        coins.add(new CoinEntity((LinkedHashMap<String, Object>) entry.getValue()));
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                }
                insertOrUpdateCoins(coins);
                emitter.onSuccess(coins);
            } else {
                emitter.onSuccess(appDatabase.getCoinsDao().getCoins());
            }
        });
    }

    private void insertOrUpdateCoins(List<CoinEntity> coins) {
        Completable.create(emitter -> {
            appDatabase.getCoinsDao().insert(coins);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public List<PriceAndVolumeSchema> getDataByDay(Integer limit, String valueFrom, String valueTo) throws IOException {
        return parseResponse(service.getDataByDay(apiKey, limit, valueFrom, valueTo).execute());
    }

    private List<PriceAndVolumeSchema> parseResponse(Response<ResponseModel> response) {
        if (response.isSuccessful() && response.body() != null && response.body().getData() != null && response.body().getData().getData() != null)
            return response.body().getData().getData();
        else return new ArrayList<>();
    }

    public List<PriceAndVolumeSchema> getDataByHour(Integer limit, String valueFrom, String valueTo) throws IOException {
        return parseResponse(service.getDataByHour(apiKey, limit, valueFrom, valueTo).execute());
    }

    public List<PriceAndVolumeSchema> getDataByMinute(Integer limit, String valueFrom, String valueTo) throws IOException {
        return parseResponse(service.getDataByMinute(apiKey, limit, valueFrom, valueTo).execute());
    }

    public Single<LinkedHashMap<String, String>> getCoinExchangeModel(String symbolFrom, String[] symbolsTo) {
        return Single.create(emitter -> {
            Response<LinkedHashMap<String, String>> response;
            try {
                response = service.getCoinExchangeModel(apiKey, symbolFrom, symbolsTo).execute();
                if (response.isSuccessful() && response.body() != null)
                    emitter.onSuccess(response.body());
                else if (response.errorBody() != null)
                    emitter.onError(new Throwable(response.errorBody().string()));
                else emitter.onError(new Throwable("Didn't get any data!"));
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
