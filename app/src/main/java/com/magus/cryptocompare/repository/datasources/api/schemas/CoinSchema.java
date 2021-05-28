
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
        "Id",
        "Url",
        "ImageUrl",
        "ContentCreatedOn",
        "Name",
        "Symbol",
        "CoinName",
        "FullName",
        "Description",
        "AssetTokenStatus",
        "Algorithm",
        "ProofType",
        "SortOrder",
        "Sponsored",
        "Taxonomy",
        "Rating",
        "IsTrading",
        "TotalCoinsMined",
        "BlockNumber",
        "NetHashesPerSecond",
        "BlockReward",
        "BlockTime",
        "AssetLaunchDate",
        "MaxSupply",
        "MktCapPenalty",
        "IsUsedInDefi",
        "IsUsedInNft"
})
public class CoinSchema {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Url")
    private String url;
    @JsonProperty("ImageUrl")
    private String imageUrl;
    @JsonProperty("ContentCreatedOn")
    private Integer contentCreatedOn;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("CoinName")
    private String coinName;
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("AssetTokenStatus")
    private String assetTokenStatus;
    @JsonProperty("Algorithm")
    private String algorithm;
    @JsonProperty("ProofType")
    private String proofType;
    @JsonProperty("SortOrder")
    private String sortOrder;
    @JsonProperty("Sponsored")
    private Boolean sponsored;
    @JsonProperty("Taxonomy")
    private Taxonomy taxonomy;
    @JsonProperty("Rating")
    private Rating rating;
    @JsonProperty("IsTrading")
    private Boolean isTrading;
    @JsonProperty("TotalCoinsMined")
    private Integer totalCoinsMined;
    @JsonProperty("BlockNumber")
    private Integer blockNumber;
    @JsonProperty("NetHashesPerSecond")
    private Integer netHashesPerSecond;
    @JsonProperty("BlockReward")
    private Integer blockReward;
    @JsonProperty("BlockTime")
    private Integer blockTime;
    @JsonProperty("AssetLaunchDate")
    private String assetLaunchDate;
    @JsonProperty("MaxSupply")
    private Integer maxSupply;
    @JsonProperty("MktCapPenalty")
    private Integer mktCapPenalty;
    @JsonProperty("IsUsedInDefi")
    private Integer isUsedInDefi;
    @JsonProperty("IsUsedInNft")
    private Integer isUsedInNft;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("Url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("ImageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("ImageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("ContentCreatedOn")
    public Integer getContentCreatedOn() {
        return contentCreatedOn;
    }

    @JsonProperty("ContentCreatedOn")
    public void setContentCreatedOn(Integer contentCreatedOn) {
        this.contentCreatedOn = contentCreatedOn;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("Symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("CoinName")
    public String getCoinName() {
        return coinName;
    }

    @JsonProperty("CoinName")
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    @JsonProperty("FullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("FullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("AssetTokenStatus")
    public String getAssetTokenStatus() {
        return assetTokenStatus;
    }

    @JsonProperty("AssetTokenStatus")
    public void setAssetTokenStatus(String assetTokenStatus) {
        this.assetTokenStatus = assetTokenStatus;
    }

    @JsonProperty("Algorithm")
    public String getAlgorithm() {
        return algorithm;
    }

    @JsonProperty("Algorithm")
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @JsonProperty("ProofType")
    public String getProofType() {
        return proofType;
    }

    @JsonProperty("ProofType")
    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    @JsonProperty("SortOrder")
    public String getSortOrder() {
        return sortOrder;
    }

    @JsonProperty("SortOrder")
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @JsonProperty("Sponsored")
    public Boolean getSponsored() {
        return sponsored;
    }

    @JsonProperty("Sponsored")
    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    @JsonProperty("Taxonomy")
    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    @JsonProperty("Taxonomy")
    public void setTaxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    @JsonProperty("Rating")
    public Rating getRating() {
        return rating;
    }

    @JsonProperty("Rating")
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @JsonProperty("IsTrading")
    public Boolean getIsTrading() {
        return isTrading;
    }

    @JsonProperty("IsTrading")
    public void setIsTrading(Boolean isTrading) {
        this.isTrading = isTrading;
    }

    @JsonProperty("TotalCoinsMined")
    public Integer getTotalCoinsMined() {
        return totalCoinsMined;
    }

    @JsonProperty("TotalCoinsMined")
    public void setTotalCoinsMined(Integer totalCoinsMined) {
        this.totalCoinsMined = totalCoinsMined;
    }

    @JsonProperty("BlockNumber")
    public Integer getBlockNumber() {
        return blockNumber;
    }

    @JsonProperty("BlockNumber")
    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    @JsonProperty("NetHashesPerSecond")
    public Integer getNetHashesPerSecond() {
        return netHashesPerSecond;
    }

    @JsonProperty("NetHashesPerSecond")
    public void setNetHashesPerSecond(Integer netHashesPerSecond) {
        this.netHashesPerSecond = netHashesPerSecond;
    }

    @JsonProperty("BlockReward")
    public Integer getBlockReward() {
        return blockReward;
    }

    @JsonProperty("BlockReward")
    public void setBlockReward(Integer blockReward) {
        this.blockReward = blockReward;
    }

    @JsonProperty("BlockTime")
    public Integer getBlockTime() {
        return blockTime;
    }

    @JsonProperty("BlockTime")
    public void setBlockTime(Integer blockTime) {
        this.blockTime = blockTime;
    }

    @JsonProperty("AssetLaunchDate")
    public String getAssetLaunchDate() {
        return assetLaunchDate;
    }

    @JsonProperty("AssetLaunchDate")
    public void setAssetLaunchDate(String assetLaunchDate) {
        this.assetLaunchDate = assetLaunchDate;
    }

    @JsonProperty("MaxSupply")
    public Integer getMaxSupply() {
        return maxSupply;
    }

    @JsonProperty("MaxSupply")
    public void setMaxSupply(Integer maxSupply) {
        this.maxSupply = maxSupply;
    }

    @JsonProperty("MktCapPenalty")
    public Integer getMktCapPenalty() {
        return mktCapPenalty;
    }

    @JsonProperty("MktCapPenalty")
    public void setMktCapPenalty(Integer mktCapPenalty) {
        this.mktCapPenalty = mktCapPenalty;
    }

    @JsonProperty("IsUsedInDefi")
    public Integer getIsUsedInDefi() {
        return isUsedInDefi;
    }

    @JsonProperty("IsUsedInDefi")
    public void setIsUsedInDefi(Integer isUsedInDefi) {
        this.isUsedInDefi = isUsedInDefi;
    }

    @JsonProperty("IsUsedInNft")
    public Integer getIsUsedInNft() {
        return isUsedInNft;
    }

    @JsonProperty("IsUsedInNft")
    public void setIsUsedInNft(Integer isUsedInNft) {
        this.isUsedInNft = isUsedInNft;
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
