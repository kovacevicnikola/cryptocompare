package com.magus.cryptocompare.repository.datasources.database;

import android.app.Application;

import androidx.room.Room;

import com.magus.cryptocompare.repository.datasources.BaseLocalDataSource;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class LocalDataSource extends BaseLocalDataSource {
    AppDatabase appDatabase;

    public LocalDataSource(Application application) {
        appDatabase = Room.databaseBuilder(application, AppDatabase.class, "Crypto-db").build();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(new Cache(httpCacheDirectory, cacheSize));
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        httpClient.readTimeout(5, TimeUnit.SECONDS);

    }

    @Override
    public void saveCoins(List<CoinEntity> coins) throws Exception {
        Completable.create(emitter -> {
            appDatabase.getCoinsDao().insert(coins);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public List<CoinEntity> getCoins() throws Exception {
        return appDatabase.getCoinsDao().getCoins();
    }
}
