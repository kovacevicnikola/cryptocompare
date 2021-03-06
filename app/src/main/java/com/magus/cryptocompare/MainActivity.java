package com.magus.cryptocompare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.magus.cryptocompare.ui.list.CryptoListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CryptoListFragment.newInstance())
                    .commitNow();
        }
    }

}