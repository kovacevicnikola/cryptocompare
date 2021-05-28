package com.magus.cryptocompare.repository.datasources.api.schemas;

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
        "BTC",
        "ETH",
        "EVN",
        "DOGE",
        "ZEC",
        "USD",
        "EUR"
})
public class CoinExchangeSchema {
    @JsonProperty("BTC")
    private Integer btc;
    @JsonProperty("ETH")
    private Double eth;
    @JsonProperty("EVN")
    private Double evn;
    @JsonProperty("DOGE")
    private Double doge;
    @JsonProperty("ZEC")
    private Double zec;
    @JsonProperty("USD")
    private Double usd;
    @JsonProperty("EUR")
    private Double eur;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override
    public String toString() {
        return "CoinExchangeModel{" +
                "btc=" + btc +
                ", eth=" + eth +
                ", evn=" + evn +
                ", doge=" + doge +
                ", zec=" + zec +
                ", usd=" + usd +
                ", eur=" + eur +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("BTC")
    public Integer getBtc() {
        return btc;
    }

    @JsonProperty("BTC")
    public void setBtc(Integer btc) {
        this.btc = btc;
    }

    @JsonProperty("ETH")
    public Double getEth() {
        return eth;
    }

    @JsonProperty("ETH")
    public void setEth(Double eth) {
        this.eth = eth;
    }

    @JsonProperty("EVN")
    public Double getEvn() {
        return evn;
    }

    @JsonProperty("EVN")
    public void setEvn(Double evn) {
        this.evn = evn;
    }

    @JsonProperty("DOGE")
    public Double getDoge() {
        return doge;
    }

    @JsonProperty("DOGE")
    public void setDoge(Double doge) {
        this.doge = doge;
    }

    @JsonProperty("ZEC")
    public Double getZec() {
        return zec;
    }

    @JsonProperty("ZEC")
    public void setZec(Double zec) {
        this.zec = zec;
    }

    @JsonProperty("USD")
    public Double getUsd() {
        return usd;
    }

    @JsonProperty("USD")
    public void setUsd(Double usd) {
        this.usd = usd;
    }

    @JsonProperty("EUR")
    public Double getEur() {
        return eur;
    }

    @JsonProperty("EUR")
    public void setEur(Double eur) {
        this.eur = eur;
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