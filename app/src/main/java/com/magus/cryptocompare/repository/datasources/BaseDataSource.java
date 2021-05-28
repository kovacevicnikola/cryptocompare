package com.magus.cryptocompare.repository.datasources;

import com.magus.cryptocompare.repository.datasources.database.CoinEntity;

import java.util.List;

public abstract class BaseDataSource {

    public abstract List<CoinEntity> getCoins() throws Exception;
}
