package com.magus.cryptocompare.repository.datasources.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CoinEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinListDao getCoinsDao();

}
