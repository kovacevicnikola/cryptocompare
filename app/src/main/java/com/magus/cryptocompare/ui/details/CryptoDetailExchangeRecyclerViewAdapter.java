package com.magus.cryptocompare.ui.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.databinding.ListItemExchangeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class CryptoDetailExchangeRecyclerViewAdapter extends RecyclerView.Adapter<CryptoDetailExchangeRecyclerViewAdapter.ExchangeViewHolder> {
    LinkedHashMap<String, String> data = new LinkedHashMap<>();

    public CryptoDetailExchangeRecyclerViewAdapter(LinkedHashMap<String, String> labelValueHashMap) {
        data.putAll(labelValueHashMap);

    }

    @NonNull
    @NotNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CryptoDetailExchangeRecyclerViewAdapter.ExchangeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exchange, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptoDetailExchangeRecyclerViewAdapter.ExchangeViewHolder holder, int position) {
        holder.binding.tvLabelAndValue.setText(String.format("%s : %s", data.keySet().toArray(new String[0])[position], data.values().toArray(new String[0])[position]));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void bindData(LinkedHashMap<String, String> data) {
        this.data.putAll(data);
        notifyDataSetChanged();
    }

    class ExchangeViewHolder extends RecyclerView.ViewHolder {
        ListItemExchangeBinding binding;

        public ExchangeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = ListItemExchangeBinding.bind(itemView);
        }
    }
}
