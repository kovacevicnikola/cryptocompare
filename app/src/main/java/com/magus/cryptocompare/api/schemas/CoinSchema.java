package com.magus.cryptocompare.api.schemas;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoinSchema {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("ImageUrl")
    private String imageUrl;
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("FullName")
    private String fullName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public CoinSchema(LinkedHashMap<String, String> value) {
        imageUrl = value.get("ImageUrl");
        symbol = value.get("Symbol");
        fullName = value.get("FullName");
        id = value.get("Id");
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", symbol='" + symbol + '\'' +
                ", fullName='" + fullName + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("ImageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("ImageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("Symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("Symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("FullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("FullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
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