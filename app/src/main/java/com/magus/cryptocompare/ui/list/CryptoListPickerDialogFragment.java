package com.magus.cryptocompare.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.magus.cryptocompare.R;
import com.magus.cryptocompare.databinding.FragmentCryptoListBinding;
import com.magus.cryptocompare.pojo.OnCryptoPickedListener;
import com.magus.cryptocompare.repository.MainViewModel;
import com.magus.cryptocompare.repository.datasources.database.CoinEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.magus.cryptocompare.ui.details.CryptoDetailGraphFragment.REQUEST_CODE_TSYM;

public class CryptoListPickerDialogFragment extends DialogFragment implements OnCryptoPickedListener {
    public static String ARG_TO_SYMBOL = "arg_symbol";
    private FragmentCryptoListBinding binding;
    private CryptoRecyclerViewAdapter adapter;
    private MainViewModel mViewModel;

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
                Timber.e(e);
            }
        });
    }

    @Override
    public void onCryptoPicked(CoinEntity coinEntity) {
        Bundle result = new Bundle();
        result.putString(ARG_TO_SYMBOL, coinEntity.getSymbol());
        getParentFragmentManager().setFragmentResult(REQUEST_CODE_TSYM, result);
        dismiss();
    }
}
