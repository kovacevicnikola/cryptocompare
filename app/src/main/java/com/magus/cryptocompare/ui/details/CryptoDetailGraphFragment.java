package com.magus.cryptocompare.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.magus.cryptocompare.databinding.FragmentCryptoDetailGraphBinding;
import com.magus.cryptocompare.datasource.MainViewModel;
import com.magus.cryptocompare.pojo.PathDataModel;
import com.magus.cryptocompare.ui.list.CryptoListPickerDialogFragment;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.magus.cryptocompare.datasource.MainViewModel.TimeIncrementType.BYMINUTE;
import static com.magus.cryptocompare.datasource.MainViewModel.TimeIncrementType.DAILY;
import static com.magus.cryptocompare.datasource.MainViewModel.TimeIncrementType.HOURLY;
import static com.magus.cryptocompare.ui.list.CryptoListPickerDialogFragment.ARG_TO_SYMBOL;

public class CryptoDetailGraphFragment extends BaseCryptoDetailsFragment {
    FragmentCryptoDetailGraphBinding binding;
    public static String REQUEST_CODE_TSYM = "1337";
    String toSymbol = "BTC";

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
        refreshGraphs();
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
        binding.fab.setOnClickListener(v -> {
            new CryptoListPickerDialogFragment().show(getChildFragmentManager(), null);
            getChildFragmentManager().setFragmentResultListener(REQUEST_CODE_TSYM, getViewLifecycleOwner(), (requestKey, result) -> {
                if (requestKey.equals(REQUEST_CODE_TSYM)) {
                    toSymbol = result.getString(ARG_TO_SYMBOL);
                    refreshGraphs();
                }
            });
        });
    }

    private void refreshGraphs() {
        binding.tvFab.setText(toSymbol);
        initGraph(HOURLY);
        initGraph(DAILY);
        initGraph(BYMINUTE);
    }

    private void initGraph(MainViewModel.TimeIncrementType type) {
        mViewModel.getGraphPathAndData(type, symbol, toSymbol).subscribeOn(Schedulers.io())
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

                switch (type) {
                    case DAILY:
                        binding.graphDay.graphView.setVisibility(View.VISIBLE);
                        binding.graphDay.tvError.setVisibility(View.GONE);
                        binding.graphDay.graphView.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.graphDay.tvHighValue.setText(String.format(Locale.getDefault(), "%f", path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.graphDay.tvLowValue.setText(String.format(Locale.getDefault(), "%f", path.getMinHigh()));
                        if (maxTime != null)
                            binding.graphDay.tvTimeRight.setText(parseDate(maxTime, type));
                        if (minTime != null)
                            binding.graphDay.tvTimeLeft.setText(parseDate(minTime, type));
                        break;
                    case BYMINUTE:
                        binding.graphMinute.graphView.setVisibility(View.VISIBLE);
                        binding.graphMinute.tvError.setVisibility(View.GONE);
                        binding.graphMinute.graphView.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.graphMinute.tvHighValue.setText(String.format(Locale.getDefault(), "%f", path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.graphMinute.tvLowValue.setText(String.format(Locale.getDefault(), "%f", path.getMinHigh()));
                        if (maxTime != null)
                            binding.graphMinute.tvTimeRight.setText(parseDate(maxTime, type));
                        if (minTime != null)
                            binding.graphMinute.tvTimeLeft.setText(parseDate(minTime, type));

                        break;
                    case HOURLY:
                        binding.graphHour.graphView.setVisibility(View.VISIBLE);
                        binding.graphHour.tvError.setVisibility(View.GONE);
                        binding.graphHour.graphView.drawGraph(path, mViewModel.getPaint(type));
                        if (path.getMaxHigh() != Double.MIN_VALUE)
                            binding.graphHour.tvHighValue.setText(String.format(Locale.getDefault(), "%f", path.getMaxHigh()));
                        if (path.getMinHigh() != Double.MAX_VALUE)
                            binding.graphHour.tvLowValue.setText(String.format(Locale.getDefault(), "%f", path.getMinHigh()));
                        if (maxTime != null)
                            binding.graphHour.tvTimeRight.setText(parseDate(maxTime, type));
                        if (minTime != null)
                            binding.graphHour.tvTimeLeft.setText(parseDate(minTime, type));

                        break;
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                handleErrorByType(e.getMessage(), type);
            }
        });
    }

    private void handleErrorByType(String message, MainViewModel.TimeIncrementType type) {
        if (isAdded()) {
            switch (type) {
                case HOURLY: {
                    binding.graphHour.graphView.setVisibility(View.GONE);
                    binding.graphHour.tvError.setText(message);
                    binding.graphHour.tvError.setVisibility(View.VISIBLE);
                }
                case DAILY: {
                    binding.graphDay.graphView.setVisibility(View.GONE);

                    binding.graphDay.tvError.setText(message);
                    binding.graphDay.tvError.setVisibility(View.VISIBLE);

                }
                case BYMINUTE: {
                    binding.graphMinute.graphView.setVisibility(View.GONE);
                    binding.graphMinute.tvError.setText(message);
                    binding.graphMinute.tvError.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    private String parseDate(LocalDateTime dateTime, MainViewModel.TimeIncrementType type) {
        try {
            switch (type) {
                case HOURLY:
                    return dateTime.format(DateTimeFormatter.ofPattern("dd.MM HH:mm").withLocale(Locale.getDefault()));
                case DAILY:
                    return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.").withLocale(Locale.getDefault()));
                case BYMINUTE:
                    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.getDefault()));
            }
        } catch (Exception e) {
            return "";
        }
        return "";


    }
}
