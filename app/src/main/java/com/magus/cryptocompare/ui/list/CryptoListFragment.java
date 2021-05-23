package com.magus.cryptocompare.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.databinding.FragmentCryptoListBinding;
import com.magus.cryptocompare.datasource.MainViewModel;
import com.magus.cryptocompare.datasource.database.CoinEntity;
import com.magus.cryptocompare.pojo.OnCryptoPickedListener;
import com.magus.cryptocompare.ui.details.CryptoDetailFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CryptoListFragment extends Fragment implements OnCryptoPickedListener {

    private MainViewModel mViewModel;
    private FragmentCryptoListBinding binding;
    private CryptoRecyclerViewAdapter adapter;

    public static CryptoListFragment newInstance() {
        return new CryptoListFragment();
    }

    @Override
    public void onCryptoPicked(CoinEntity coin) {
        getParentFragmentManager().beginTransaction().replace(R.id.container, CryptoDetailFragment.newInstance(coin))
                .addToBackStack(null).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCryptoListBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        adapter = new CryptoRecyclerViewAdapter(getString(R.string.base_image_url), this);
        binding.rvCrypto.setAdapter(adapter);
        binding.rvCrypto.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel.getCoins().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<CoinEntity>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(@NotNull List<CoinEntity> coins) {
                binding.progressBar.setVisibility(View.GONE);
                if (coins.isEmpty() && isAdded())
                    Toast.makeText(getContext(), "Dataset seems to be empty!", Toast.LENGTH_LONG).show();
                else adapter.bindData(coins);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                handleError(e.getMessage());
            }
        });
    }


    protected void handleError(String message) {
        if (isAdded()) Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
