package com.magus.cryptocompare.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.magus.cryptocompare.databinding.FragmentCryptoDetailGraphBinding;
import com.magus.cryptocompare.ui.main.MainViewModel;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.magus.cryptocompare.ui.main.MainViewModel.TimeIncrementType.BYMINUTE;
import static com.magus.cryptocompare.ui.main.MainViewModel.TimeIncrementType.DAILY;
import static com.magus.cryptocompare.ui.main.MainViewModel.TimeIncrementType.HOURLY;

public class CryptoDetailGraphFragment extends BaseCryptoDetailsFragment {
    FragmentCryptoDetailGraphBinding binding;

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
                    if (checkedId == binding.bDaily1.getId()) mViewModel.putLimit(DAILY, 1);
                    else if (checkedId == binding.bDaily7.getId()) mViewModel.putLimit(DAILY, 7);
                    else if (checkedId == binding.bDaily14.getId()) mViewModel.putLimit(DAILY, 14);
                    else if (checkedId == binding.bDaily30.getId()) mViewModel.putLimit(DAILY, 30);

                    initGraph(DAILY);

                }
            }
        });
        binding.btgMinute.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == binding.bMinute60.getId()) mViewModel.putLimit(BYMINUTE, 60);
                    else if (checkedId == binding.bMinute180.getId())
                        mViewModel.putLimit(BYMINUTE, 180);
                    else if (checkedId == binding.bMinute1440.getId())
                        mViewModel.putLimit(BYMINUTE, 1440);
                    initGraph(BYMINUTE);

                }

            }
        });
        binding.btgHourly.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == binding.bHourly24.getId()) mViewModel.putLimit(HOURLY, 24);
                    else if (checkedId == binding.bHourly72.getId())
                        mViewModel.putLimit(HOURLY, 72);
                    else if (checkedId == binding.bHourly168.getId())
                        mViewModel.putLimit(HOURLY, 168);
                    initGraph(HOURLY);

                }
            }
        });
    }

    private void initGraph(MainViewModel.TimeIncrementType type) {
        mViewModel.getGraphPathAndData(type, symbol, "BTC").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<PathDataModel>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onSuccess(@NotNull PathDataModel path) {
                LocalDateTime minTime = null;
                LocalDateTime maxTime = null;
                if (path.getMinTime() != Long.MAX_VALUE)
                    minTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(path.getMinTime()), ZoneId.systemDefault());
                if (path.getMaxTime() != Long.MIN_VALUE)
                    maxTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(path.getMaxTime()), ZoneId.systemDefault());

                //binding.gvDaily.drawGraph(path, mViewModel.getPaint(type));
                switch (type) {
                    case DAILY:
                        binding.gvDaily.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.tvHighValueDay.setText(Double.toString(path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.tvLowValueDay.setText(Double.toString(path.getMinHigh()));
                        if (maxTime != null)
                            binding.tvHighTimeDay.setText(parseDate(maxTime, type));
                        if (minTime != null) binding.tvLowTimeDay.setText(parseDate(minTime, type));


                        break;
                    case BYMINUTE:
                        binding.gvMinute.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.tvHighValueMinute.setText(Double.toString(path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.tvLowValueMinute.setText(Double.toString(path.getMinHigh()));
                        if (maxTime != null)
                            binding.tvHighTimeMinute.setText(parseDate(maxTime, type));
                        if (minTime != null)
                            binding.tvLowTimeMinute.setText(parseDate(minTime, type));
                        break;
                    case HOURLY:
                        binding.gvHourly.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.tvHighValueHour.setText(Double.toString(path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.tvLowValueHour.setText(Double.toString(path.getMinHigh()));
                        if (maxTime != null)
                            binding.tvHighTimeHourly.setText(parseDate(maxTime, type));
                        if (minTime != null)
                            binding.tvLowTimeHourly.setText(parseDate(minTime, type));
                        break;
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                handleError(e.getMessage());
            }
        });
    }

    private String parseDate(LocalDateTime dateTime, MainViewModel.TimeIncrementType type) {
        try {
            switch (type) {
                case HOURLY:
                case DAILY:
                    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                case BYMINUTE:
                    return dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
            }
        } catch (Exception e) {
            return "";
        }
        return "";


    }
}
