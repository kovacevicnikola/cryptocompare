package com.magus.cryptocompare.datasource.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class CoinListDao extends BaseDao<CoinEntity> {


    @Query("SELECT * FROM Coins")
    public abstract List<CoinEntity> getCoins();
}
