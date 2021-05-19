package com.magus.cryptocompare.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magus.cryptocompare.databinding.FragmentCryptoDetailExchangeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CryptoDetailExchangeFragment extends BaseCryptoDetailsFragment {
    public static final String[] EXCHANGE_SYMBOLS = new String[]{"BTC", "ETH", "EVN", "DOGE", "ZEC", "USD", "EUR"};
    FragmentCryptoDetailExchangeBinding binding;
    CryptoDetailExchangeRecyclerViewAdapter adapter;

    public static CryptoDetailExchangeFragment newInstance(String symbol) {

        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL, symbol);
        CryptoDetailExchangeFragment fragment = new CryptoDetailExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        adapter = new CryptoDetailExchangeRecyclerViewAdapter();
        binding.rvExchangeRates.setAdapter(adapter);
        binding.rvExchangeRates.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mViewModel.getCoinExchangeRate(symbol, EXCHANGE_SYMBOLS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<LinkedHashMap<String, String>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull LinkedHashMap<String, String> linkedHashMap) {
                        adapter.bindData(linkedHashMap);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        handleError(e.getMessage());
                    }
                });
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCryptoDetailExchangeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
