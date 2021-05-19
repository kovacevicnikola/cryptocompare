package com.magus.cryptocompare.ui.details;

import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.magus.cryptocompare.databinding.FragmentCryptoDetailGraphBinding;
import com.magus.cryptocompare.ui.main.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.magus.cryptocompare.ui.main.MainViewModel.CryptoDataType.BYMINUTE;
import static com.magus.cryptocompare.ui.main.MainViewModel.CryptoDataType.DAILY;
import static com.magus.cryptocompare.ui.main.MainViewModel.CryptoDataType.HOURLY;

public class CryptoDetailGraphFragment extends BaseCryptoDetailsFragment {
    FragmentCryptoDetailGraphBinding binding;
    HashMap<MainViewModel.CryptoDataType, Integer> limitHashMap = new HashMap<>();

    public static CryptoDetailGraphFragment newInstance(String symbol) {

        Bundle args = new Bundle();
        args.putString(ARG_SYMBOL, symbol);
        CryptoDetailGraphFragment fragment = new CryptoDetailGraphFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCryptoDetailGraphBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        initGraph(HOURLY);
        initGraph(DAILY);
        initGraph(BYMINUTE);
        binding.btgDaily.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == binding.bDaily1.getId()) limitHashMap.put(DAILY, 7);
                    else if (checkedId == binding.bDaily3.getId()) limitHashMap.put(DAILY, 14);
                    else if (checkedId == binding.bDaily5.getId()) limitHashMap.put(DAILY, 28);
                    initGraph(DAILY);

                }
            }
        });
        binding.btgMinute.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == binding.bMinute1.getId()) limitHashMap.put(BYMINUTE, 10);
                    else if (checkedId == binding.bMinute3.getId()) limitHashMap.put(BYMINUTE, 30);
                    else if (checkedId == binding.bMinute5.getId()) limitHashMap.put(BYMINUTE, 60);
                    initGraph(BYMINUTE);

                }

            }
        });
        binding.btgHourly.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == binding.bHourly1.getId()) limitHashMap.put(HOURLY, 8);
                    else if (checkedId == binding.bHourly3.getId()) limitHashMap.put(HOURLY, 24);
                    else if (checkedId == binding.bHourly5.getId()) limitHashMap.put(HOURLY, 60);
                    initGraph(HOURLY);

                }
            }
        });
    }

    private void initGraph(MainViewModel.CryptoDataType type) {
        mViewModel.getGraphPath(type, symbol, "BTC", limitHashMap.get(type) == null ? 10 : limitHashMap.get(type)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Path>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onSuccess(@NotNull Path path) {
                //binding.gvDaily.drawGraph(path, mViewModel.getPaint(type));
                switch (type) {
                    case DAILY:
                        binding.gvDaily.drawGraph(path, mViewModel.getPaint(type));
                        break;
                    case BYMINUTE:
                        binding.gvMinute.drawGraph(path, mViewModel.getPaint(type));
                        break;
                    case HOURLY:
                        binding.gvHourly.drawGraph(path, mViewModel.getPaint(type));
                        break;
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Timber.e(e);
                Toast.makeText(getContext(), "It appears we can't get data for the specified crypto!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
