package com.magus.cryptocompare.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.api.schemas.Coin;
import com.magus.cryptocompare.databinding.MainFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CryptoListFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    private CryptoRecyclerViewAdapter adapter;

    public static CryptoListFragment newInstance() {
        return new CryptoListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        adapter = new CryptoRecyclerViewAdapter(getString(R.string.base_image_url), getParentFragmentManager());
        binding.rvCrypto.setAdapter(adapter);
        binding.rvCrypto.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel.getCoins().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Coin>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onSuccess(@NotNull List<Coin> coins) {
                adapter.bindData(coins);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Timber.e(e);
            }
        });
    }
}