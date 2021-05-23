package com.magus.cryptocompare.pojo;

import com.magus.cryptocompare.datasource.database.CoinEntity;

public interface OnCryptoPickedListener {
    public void onCryptoPicked(CoinEntity coin);
}
