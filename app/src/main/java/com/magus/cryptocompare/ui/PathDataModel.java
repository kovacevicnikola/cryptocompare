package com.magus.cryptocompare.ui;

import android.graphics.Path;

public class PathDataModel {
    Path path;
    Background background;
    Double minHigh;
    Long minTime;
    Double maxHigh;
    Long maxTime;

    public PathDataModel(Path path, Double minHigh, Long minTime, Double maxHigh, Long maxTime, Background background) {
        this.path = path;
        this.minHigh = minHigh;
        this.minTime = minTime;
        this.maxHigh = maxHigh;
        this.maxTime = maxTime;
        this.background = background;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Double getMinHigh() {
        return minHigh;
    }

    public void setMinHigh(Double minHigh) {
        this.minHigh = minHigh;
    }

    public Long getMinTime() {
        return minTime;
    }

    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    public Double getMaxHigh() {
        return maxHigh;
    }

    public void setMaxHigh(Double maxHigh) {
        this.maxHigh = maxHigh;
    }

    public Long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }
}
