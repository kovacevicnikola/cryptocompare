package com.magus.cryptocompare.pojo;

import android.graphics.Paint;
import android.graphics.Rect;

public class Background {
    private int pixelInterval;
    private float chartWidth;
    private float chartHeight;
    private Paint paint;
    private Rect rect;

    public Background(int pixelInterval, float chartWidth, float chartHeight, Paint paint) {
        this.pixelInterval = pixelInterval;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        this.paint = paint;
        this.rect = new Rect(0, 0, (int) chartWidth - 4, (int) chartHeight - 4);
    }

    public int getPixelInterval() {
        return pixelInterval;
    }

    public void setPixelInterval(int pixelInterval) {
        this.pixelInterval = pixelInterval;
    }

    public float getChartWidth() {
        return chartWidth;
    }

    public void setChartWidth(float chartWidth) {
        this.chartWidth = chartWidth;
    }

    public float getChartHeight() {
        return chartHeight;
    }

    public void setChartHeight(float chartHeight) {
        this.chartHeight = chartHeight;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Rect getRect() {
        return rect;
    }
}
