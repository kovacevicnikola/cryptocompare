package com.magus.cryptocompare.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.magus.cryptocompare.ui.main.MainViewModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;

public class BaseCryptoDetailsFragment extends Fragment {
    protected static final String ARG_SYMBOL = "ARG_SYMBOL";
    protected MainViewModel mViewModel;
    String symbol;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (getArguments() != null) {
            symbol = getArguments().getString(ARG_SYMBOL);
        }
    }

    protected void handleError(String message) {
        if (isAdded()) Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
