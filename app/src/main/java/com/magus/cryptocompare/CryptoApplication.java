package com.magus.cryptocompare;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;
import androidx.viewbinding.BuildConfig;

import com.jakewharton.threetenabp.AndroidThreeTen;

import timber.log.Timber;

public class CryptoApplication extends Application {


    @Override
    public void onCreate() {
        // ThreeTenBP for times and dates, called before super to be available for objects
        AndroidThreeTen.init(this);
        super.onCreate();
        if (BuildConfig.DEBUG) enableStrictMode();

        Timber.plant(new Timber.DebugTree());

    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
