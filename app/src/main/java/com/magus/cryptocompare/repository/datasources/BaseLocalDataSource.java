package com.magus.cryptocompare.repository.datasources;

import com.magus.cryptocompare.repository.datasources.database.CoinEntity;

import java.util.List;

public abstract class BaseLocalDataSource extends BaseDataSource {
    public abstract void saveCoins(List<CoinEntity> coins) throws Exception;
}
