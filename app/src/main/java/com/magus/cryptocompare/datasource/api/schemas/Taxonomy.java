
package com.magus.cryptocompare.datasource.api.schemas;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Access",
        "FCA",
        "FINMA",
        "Industry",
        "CollateralizedAsset",
        "CollateralizedAssetType",
        "CollateralType",
        "CollateralInfo"
})
public class Taxonomy {

    @JsonProperty("Access")
    private String access;
    @JsonProperty("FCA")
    private String fca;
    @JsonProperty("FINMA")
    private String finma;
    @JsonProperty("Industry")
    private String industry;
    @JsonProperty("CollateralizedAsset")
    private String collateralizedAsset;
    @JsonProperty("CollateralizedAssetType")
    private String collateralizedAssetType;
    @JsonProperty("CollateralType")
    private String collateralType;
    @JsonProperty("CollateralInfo")
    private String collateralInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Taxonomy(Object taxonomy) {
        Timber.e(taxonomy.getClass().getCanonicalName());
    }

    @JsonProperty("Access")
    public String getAccess() {
        return access;
    }

    @JsonProperty("Access")
    public void setAccess(String access) {
        this.access = access;
    }

    @JsonProperty("FCA")
    public String getFca() {
        return fca;
    }

    @JsonProperty("FCA")
    public void setFca(String fca) {
        this.fca = fca;
    }

    @JsonProperty("FINMA")
    public String getFinma() {
        return finma;
    }

    @JsonProperty("FINMA")
    public void setFinma(String finma) {
        this.finma = finma;
    }

    @JsonProperty("Industry")
    public String getIndustry() {
        return industry;
    }

    @JsonProperty("Industry")
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @JsonProperty("CollateralizedAsset")
    public String getCollateralizedAsset() {
        return collateralizedAsset;
    }

    @JsonProperty("CollateralizedAsset")
    public void setCollateralizedAsset(String collateralizedAsset) {
        this.collateralizedAsset = collateralizedAsset;
    }

    @JsonProperty("CollateralizedAssetType")
    public String getCollateralizedAssetType() {
        return collateralizedAssetType;
    }

    @JsonProperty("CollateralizedAssetType")
    public void setCollateralizedAssetType(String collateralizedAssetType) {
        this.collateralizedAssetType = collateralizedAssetType;
    }

    @JsonProperty("CollateralType")
    public String getCollateralType() {
        return collateralType;
    }

    @JsonProperty("CollateralType")
    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    @JsonProperty("CollateralInfo")
    public String getCollateralInfo() {
        return collateralInfo;
    }

    @JsonProperty("CollateralInfo")
    public void setCollateralInfo(String collateralInfo) {
        this.collateralInfo = collateralInfo;
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
