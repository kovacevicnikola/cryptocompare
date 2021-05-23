package com.magus.cryptocompare.ui.details;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.magus.cryptocompare.databinding.FragmentCryptoDetailBinding;

import org.jetbrains.annotations.NotNull;

public class CryptoDetailFragment extends BaseCryptoDetailsFragment {
    FragmentCryptoDetailBinding binding;

    public static CryptoDetailFragment newInstance(Parcelable coin) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_COIN, coin);
        CryptoDetailFragment fragment = new CryptoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCryptoDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) return CryptoDetailExchangeFragment.newInstance(coin);
                return CryptoDetailGraphFragment.newInstance(coin);
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        };
        binding.vpToggleDisplay.setAdapter(adapter);
        new TabLayoutMediator(binding.tlToggleDisplay, binding.vpToggleDisplay, (this::setTextByPosition)).attach();

    }

    private void setTextByPosition(TabLayout.Tab tab, int i) {
        if (i == 0) tab.setText(coin.getSymbol());
        else tab.setText("Graphs");
    }
}
