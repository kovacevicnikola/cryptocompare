package com.magus.cryptocompare.repository;

import android.app.Application;

import com.magus.cryptocompare.repository.datasources.BaseLocalDataSource;
import com.magus.cryptocompare.repository.datasources.BaseRemoteDataSource;
import com.magus.cryptocompare.repository.datasources.api.RemoteDataSource;
import com.magus.cryptocompare.repository.datasources.api.schemas.PriceAndVolumeSchema;
import com.magus.cryptocompare.repository.datasources.database.CoinEntity;
import com.magus.cryptocompare.repository.datasources.database.LocalDataSource;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;

public class MainRepository implements IMainRepository {

    BaseRemoteDataSource remoteDataSource;
    BaseLocalDataSource localDataSource;

    public MainRepository(Application application) {
        remoteDataSource = new RemoteDataSource(application);
        localDataSource = new LocalDataSource(application);

    }

    @Override
    public List<CoinEntity> getCryptoCoinList() throws Exception {
        try {
            List<CoinEntity> coins = remoteDataSource.getCoins();
            if (coins != null && !coins.isEmpty()) {
                localDataSource.saveCoins(coins);
                return coins;
            } else {
                return localDataSource.getCoins();
            }
        } catch (Exception e) {
            return localDataSource.getCoins();
        }
    }

    @Override

    public List<PriceAndVolumeSchema> getDataByDay(Integer limit, String valueFrom, String valueTo) throws IOException {
        return remoteDataSource.getDataByDay(limit, valueFrom, valueTo);
    }

    @Override

    public List<PriceAndVolumeSchema> getDataByHour(Integer limit, String valueFrom, String valueTo) throws IOException {
        return remoteDataSource.getDataByDay(limit, valueFrom, valueTo);
    }

    @Override

    public List<PriceAndVolumeSchema> getDataByMinute(Integer limit, String valueFrom, String valueTo) throws IOException {
        return remoteDataSource.getDataByDay(limit, valueFrom, valueTo);
    }

    @Override

    public Single<LinkedHashMap<String, String>> getCoinExchangeModel(String symbolFrom, String[] symbolsTo) {
        return remoteDataSource.getCoinExchangeModel(symbolFrom, symbolsTo);
    }
}
