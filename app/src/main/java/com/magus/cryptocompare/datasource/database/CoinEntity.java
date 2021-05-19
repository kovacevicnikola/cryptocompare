package com.magus.cryptocompare.datasource.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.LinkedHashMap;

@Entity(tableName = "Coins", indices = {@Index(value = "symbol", unique = true)})
public class CoinEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imageUrl;
    private String symbol;
    private String fullName;

    //unpacker constructor
    public CoinEntity(LinkedHashMap<String, String> value) {
        imageUrl = value.get("ImageUrl");
        symbol = value.get("Symbol");
        fullName = value.get("FullName");
    }

    //room constructor
    public CoinEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
