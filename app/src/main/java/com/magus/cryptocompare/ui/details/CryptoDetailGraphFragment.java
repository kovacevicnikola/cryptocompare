package com.magus.cryptocompare.ui.details;

import android.os.Bundle;

public class CryptoDetailGraphFragment extends BaseCryptoDetailsFragment {
    public static CryptoDetailExchangeFragment newInstance(String symbol) {

        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL, symbol);
        CryptoDetailExchangeFragment fragment = new CryptoDetailExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
