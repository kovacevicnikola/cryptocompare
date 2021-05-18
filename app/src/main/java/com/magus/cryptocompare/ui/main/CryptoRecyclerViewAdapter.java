package com.magus.cryptocompare.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magus.cryptocompare.R;
import com.magus.cryptocompare.api.schemas.Coin;
import com.magus.cryptocompare.databinding.ListItemCryptoBinding;
import com.magus.cryptocompare.ui.details.CryptoDetailFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CryptoRecyclerViewAdapter extends RecyclerView.Adapter<CryptoRecyclerViewAdapter.CryptoViewHolder> {
    List<Coin> coinList = new ArrayList<>();
    String baseURL;
    FragmentManager fragmentManager;

    public CryptoRecyclerViewAdapter(String baseURL, FragmentManager fragmentManager) {
        this.baseURL = baseURL;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @NotNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CryptoViewHolder(ListItemCryptoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptoViewHolder holder, int position) {
        Coin currentCoin = coinList.get(position);
        holder.binding.tvCryptoCode.setText(currentCoin.getSymbol());
        holder.binding.tvCryptoName.setText(currentCoin.getFullName());
        Glide.with(holder.binding.ivCryptoIcon)
                .load(baseURL + currentCoin.getImageUrl()).override(120, 120)
                .error(R.drawable.ic_no_image_available)
                .into(holder.binding.ivCryptoIcon);
        holder.binding.getRoot().setOnClickListener(v -> {
            fragmentManager.beginTransaction().replace(R.id.container, CryptoDetailFragment.newInstance(currentCoin.getSymbol())).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public void bindData(List<Coin> coinList) {
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
