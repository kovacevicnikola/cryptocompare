package com.magus.cryptocompare.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magus.cryptocompare.R;
import com.magus.cryptocompare.databinding.ListItemCryptoBinding;
import com.magus.cryptocompare.datasource.database.CoinEntity;
import com.magus.cryptocompare.pojo.OnCryptoPickedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CryptoRecyclerViewAdapter extends RecyclerView.Adapter<CryptoRecyclerViewAdapter.CryptoViewHolder> {
    List<CoinEntity> coinList = new ArrayList<>();
    String baseURL;
    OnCryptoPickedListener listener;
    public CryptoRecyclerViewAdapter(String baseURL, OnCryptoPickedListener listener) {
        this.baseURL = baseURL;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CryptoViewHolder(ListItemCryptoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptoViewHolder holder, int position) {
        CoinEntity currentCoin = coinList.get(position);
        holder.binding.tvCryptoCode.setText(currentCoin.getSymbol());
        holder.binding.tvCryptoName.setText(currentCoin.getFullName());
        Glide.with(holder.binding.ivCryptoIcon)
                .load(baseURL + currentCoin.getImageUrl()).override(120, 120)
                .error(R.drawable.ic_no_image_available)
                .into(holder.binding.ivCryptoIcon);
        holder.binding.getRoot().setOnClickListener(v -> listener.onCryptoPicked(currentCoin.getSymbol()));
    }

    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public void bindData(List<CoinEntity> coinList) {
        this.coinList = coinList;
        notifyDataSetChanged();

    }

    class CryptoViewHolder extends RecyclerView.ViewHolder {
        ListItemCryptoBinding binding;

        public CryptoViewHolder(@NonNull @NotNull ListItemCryptoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
