package com.magus.cryptocompare.ui.main;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.magus.cryptocompare.datasource.CryptoDataSource;
import com.magus.cryptocompare.datasource.api.schemas.PriceAndVolumeSchema;
import com.magus.cryptocompare.datasource.database.CoinEntity;
import com.magus.cryptocompare.ui.details.PathDataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;

public class MainViewModel extends AndroidViewModel {
    private static float CHART_HEIGHT_DP = 300f;
    private Paint paintDaily;
    private Paint paintHourly;
    private Paint paintMinute;
    HashMap<TimeIncrementType, Integer> limitHashMap = new HashMap<>();
    private CryptoDataSource dataSource;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        dataSource = new CryptoDataSource(application);
        limitHashMap.put(TimeIncrementType.DAILY, 1);
        limitHashMap.put(TimeIncrementType.HOURLY, 24);
        limitHashMap.put(TimeIncrementType.BYMINUTE, 60);
        generatePaint();

    }

    public void putLimit(TimeIncrementType type, Integer limit) {
        limitHashMap.put(type, limit);
    }

    private void generatePaint() {
        paintDaily = new Paint();
        paintDaily.setAntiAlias(true);
        paintDaily.setStrokeWidth(3);
        paintDaily.setColor(Color.GREEN);
        paintDaily.setStyle(Paint.Style.STROKE);
        paintHourly = new Paint();
        paintHourly.setAntiAlias(true);
        paintHourly.setStrokeWidth(3);
        paintDaily.setTextSize(24);

        paintHourly.setColor(Color.BLACK);
        paintHourly.setStyle(Paint.Style.STROKE);
        paintMinute = new Paint();
        paintMinute.setAntiAlias(true);
        paintMinute.setStrokeWidth(3);
        paintMinute.setColor(Color.RED);
        paintMinute.setStyle(Paint.Style.STROKE);
    }

    public Paint getPaint(TimeIncrementType data) {
        switch (data) {
            case DAILY:
                return paintDaily;
            case BYMINUTE:
                return paintMinute;
            case HOURLY:
                return paintHourly;
        }
        return null;

    }

    Single<List<CoinEntity>> getCoins() {
        return dataSource.getCryptoCoinList();
    }

    public Single<PathDataModel> getGraphPathAndData(TimeIncrementType type, String valueFrom, String valueTo) {
        Integer limit = limitHashMap.get(type);
        return Single.create(emitter -> {
            Resources r = getApplication().getResources();
            List<PriceAndVolumeSchema> priceAndVolumeList = new ArrayList<>();
            Path path = new Path();
            boolean initial = true;

            float chartHeightPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    CHART_HEIGHT_DP,
                    r.getDisplayMetrics()
            );
            float chartWidthPx = r.getDisplayMetrics().widthPixels;
            try {
                switch (type) {
                    case DAILY:
                        priceAndVolumeList = dataSource.getDataByDay(limit, valueFrom, valueTo);

                        break;
                    case HOURLY:
                        priceAndVolumeList = dataSource.getDataByHour(limit, valueFrom, valueTo);
                        break;

                    case BYMINUTE:
                        priceAndVolumeList = dataSource.getDataByMinute(limit, valueFrom, valueTo);

                        break;

                }
                if (priceAndVolumeList.isEmpty()) {
                    emitter.onError(new Throwable("Dataset seems to be empty!"));
                    return;
                }
                Double maxHigh = Double.MIN_NORMAL;
                Long maxTime = Long.MIN_VALUE;
                Double minHigh = Double.MAX_VALUE;
                Long minTime = Long.MAX_VALUE;
                for (PriceAndVolumeSchema priceAndVolumeSchema : priceAndVolumeList) {
                    if (priceAndVolumeSchema.getHigh() > maxHigh)
                        maxHigh = priceAndVolumeSchema.getHigh();
                    if (priceAndVolumeSchema.getHigh() < minHigh)
                        minHigh = priceAndVolumeSchema.getHigh();
                    if (priceAndVolumeSchema.getTime() > maxTime)
                        maxTime = priceAndVolumeSchema.getTime();
                    if (priceAndVolumeSchema.getTime() < minTime)
                        minTime = priceAndVolumeSchema.getTime();
                }

                for (PriceAndVolumeSchema priceAndVolumeSchema : priceAndVolumeList) {
                    float y = (priceAndVolumeSchema.getHigh().floatValue() - minHigh.floatValue()) / (maxHigh.floatValue() - minHigh.floatValue()) * chartHeightPx;
                    float x = (priceAndVolumeSchema.getTime().floatValue() - minTime) / (maxTime.floatValue() - minTime) * chartWidthPx;

                    if (initial) {
                        path.moveTo(0, chartHeightPx - y);
                        initial = false;
                    } else path.lineTo(x, chartHeightPx - y);
                }
                emitter.onSuccess(new PathDataModel(path, minHigh, minTime, maxHigh, maxTime));
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<LinkedHashMap<String, String>> getCoinExchangeRate(String symbolFrom, String[] symbolsTo) {
        return dataSource.getCoinExchangeModel(symbolFrom, symbolsTo);
    }

    public enum TimeIncrementType {
        DAILY, HOURLY, BYMINUTE
    }


}