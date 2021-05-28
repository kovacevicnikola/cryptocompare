package com.magus.cryptocompare.repository.datasources;

import com.magus.cryptocompare.repository.datasources.api.schemas.PriceAndVolumeSchema;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;

public abstract class BaseRemoteDataSource extends BaseDataSource {

    public abstract List<PriceAndVolumeSchema> getDataByDay(Integer limit, String valueFrom, String valueTo) throws IOException;

    public abstract List<PriceAndVolumeSchema> getDataByHour(Integer limit, String valueFrom, String valueTo) throws IOException;

    public abstract List<PriceAndVolumeSchema> getDataByMinute(Integer limit, String valueFrom, String valueTo) throws IOException;

    public abstract Single<LinkedHashMap<String, String>> getCoinExchangeModel(String symbolFrom, String[] symbolsTo);


}
