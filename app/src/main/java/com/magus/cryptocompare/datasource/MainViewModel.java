package com.magus.cryptocompare.datasource;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.magus.cryptocompare.datasource.api.schemas.PriceAndVolumeSchema;
import com.magus.cryptocompare.datasource.database.CoinEntity;
import com.magus.cryptocompare.pojo.Background;
import com.magus.cryptocompare.pojo.PathDataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {
    private static float CHART_HEIGHT_DP = 340f;
    private Paint paintDaily;
    private Paint paintHourly;
    private Paint paintMinute;
    float chartHeightPx;
    HashMap<TimeIncrementType, Integer> limitHashMap = new HashMap<>();
    private CryptoDataSource dataSource;
    private Paint backgroundPaint;
    float chartWidthPx;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        dataSource = new CryptoDataSource(application);
        limitHashMap.put(TimeIncrementType.DAILY, 1);
        limitHashMap.put(TimeIncrementType.HOURLY, 24);
        limitHashMap.put(TimeIncrementType.BYMINUTE, 60);
        Resources r = getApplication().getResources();

        chartHeightPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                CHART_HEIGHT_DP,
                r.getDisplayMetrics()
        );
        chartWidthPx = r.getDisplayMetrics().widthPixels;//padding
        Timber.d("chartWidth %f", chartWidthPx);
        Timber.d("chartHeight %f", chartHeightPx);

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
        paintHourly.setColor(Color.CYAN);
        paintHourly.setStyle(Paint.Style.STROKE);

        paintMinute = new Paint();
        paintMinute.setAntiAlias(true);
        paintMinute.setStrokeWidth(3);
        paintMinute.setColor(Color.RED);
        paintMinute.setStyle(Paint.Style.STROKE);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(1);
        backgroundPaint.setColor(Color.GRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
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

    public Single<List<CoinEntity>> getCoins() {
        return dataSource.getCryptoCoinList();
    }

    public Single<PathDataModel> getGraphPathAndData(TimeIncrementType type, String valueFrom, String valueTo) {
        Integer limit = limitHashMap.get(type);
        return Single.create(emitter -> {
            List<PriceAndVolumeSchema> priceAndVolumeList = new ArrayList<>();
            Path path = new Path();
            boolean initial = true;

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
                if (maxHigh == 0) {
                    emitter.onError(new Throwable("All values for this graph are 0!"));
                    return;
                } else if (maxTime.equals(minTime)) {
                    emitter.onError(new Throwable("No data for specified time period!"));
                    return;
                }

                for (PriceAndVolumeSchema priceAndVolumeSchema : priceAndVolumeList) {
                    float y = (priceAndVolumeSchema.getHigh().floatValue() - minHigh.floatValue()) / (maxHigh.floatValue() - minHigh.floatValue()) * chartHeightPx;
                    float x = (priceAndVolumeSchema.getTime().floatValue() - minTime) / (maxTime.floatValue() - minTime) * chartWidthPx;
                    x = x * 0.95f;
                    y = y * 0.95f;
                    Timber.d("%s x %f", type.toString(), x);
                    Timber.d("%s y %f", type.toString(), chartHeightPx - y);
                    if (initial) {
                        path.moveTo(0, chartHeightPx - y);
                        initial = false;
                    } else path.lineTo(x, chartHeightPx - y);
                }
                emitter.onSuccess(new PathDataModel(path, minHigh, minTime, maxHigh, maxTime, generateBackground(backgroundPaint)));
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    private Background generateBackground(Paint paint) {
        return new Background(40, chartWidthPx, chartHeightPx, paint);
    }

    public Single<LinkedHashMap<String, String>> getCoinExchangeRate(String symbolFrom, String[] symbolsTo) {
        return dataSource.getCoinExchangeModel(symbolFrom, symbolsTo);
    }

    public enum TimeIncrementType {
        DAILY, HOURLY, BYMINUTE
    }


}