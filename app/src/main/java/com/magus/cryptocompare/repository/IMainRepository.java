package com.magus.cryptocompare.repository;

import com.magus.cryptocompare.repository.datasources.api.schemas.PriceAndVolumeSchema;
import com.magus.cryptocompare.repository.datasources.database.CoinEntity;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;

public interface IMainRepository {

    List<CoinEntity> getCryptoCoinList() throws Exception;

    List<PriceAndVolumeSchema> getDataByDay(Integer limit, String valueFrom, String valueTo) throws IOException;

    List<PriceAndVolumeSchema> getDataByHour(Integer limit, String valueFrom, String valueTo) throws IOException;

    List<PriceAndVolumeSchema> getDataByMinute(Integer limit, String valueFrom, String valueTo) throws IOException;

    Single<LinkedHashMap<String, String>> getCoinExchangeModel(String symbolFrom, String[] symbolsTo);
}
