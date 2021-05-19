
package com.magus.cryptocompare.datasource.api.schemas;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "time",
        "high",
        "low",
        "open",
        "volumefrom",
        "volumeto",
        "close",
        "conversionType",
        "conversionSymbol"
})
public class PriceAndVolumeSchema {
    @JsonProperty("time")
    private Long time;
    @JsonProperty("high")
    private Double high;
    @JsonProperty("low")
    private Double low;
    @JsonProperty("open")
    private Double open;
    @JsonProperty("volumefrom")
    private Double volumefrom;
    @JsonProperty("volumeto")
    private Double volumeto;
    @JsonProperty("close")
    private Double close;
    @JsonProperty("conversionType")
    private String conversionType;
    @JsonProperty("conversionSymbol")
    private String conversionSymbol;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("time")
    public Long getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonProperty("high")
    public Double getHigh() {
        return high == null ? 0d : high;
    }

    @JsonProperty("high")
    public void setHigh(Double high) {
        this.high = high;
    }

    @JsonProperty("low")
    public Double getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(Double low) {
        this.low = low;
    }

    @JsonProperty("open")
    public Double getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Double open) {
        this.open = open;
    }

    @JsonProperty("volumefrom")
    public Double getVolumefrom() {
        return volumefrom;
    }

    @JsonProperty("volumefrom")
    public void setVolumefrom(Double volumefrom) {
        this.volumefrom = volumefrom;
    }

    @JsonProperty("volumeto")
    public Double getVolumeto() {
        return volumeto;
    }

    @JsonProperty("volumeto")
    public void setVolumeto(Double volumeto) {
        this.volumeto = volumeto;
    }

    @JsonProperty("close")
    public Double getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(Double close) {
        this.close = close;
    }

    @JsonProperty("conversionType")
    public String getConversionType() {
        return conversionType;
    }

    @JsonProperty("conversionType")
    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    @JsonProperty("conversionSymbol")
    public String getConversionSymbol() {
        return conversionSymbol;
    }

    @JsonProperty("conversionSymbol")
    public void setConversionSymbol(String conversionSymbol) {
        this.conversionSymbol = conversionSymbol;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
