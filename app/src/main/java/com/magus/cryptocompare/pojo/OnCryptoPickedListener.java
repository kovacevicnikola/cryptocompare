package com.magus.cryptocompare.pojo;

import com.magus.cryptocompare.repository.datasources.database.CoinEntity;

public interface OnCryptoPickedListener {
    public void onCryptoPicked(CoinEntity coin);
}
