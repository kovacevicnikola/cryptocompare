package com.magus.cryptocompare.datasource.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.magus.cryptocompare.datasource.api.schemas.Rating;
import com.magus.cryptocompare.datasource.api.schemas.Taxonomy;

import java.util.LinkedHashMap;

@Entity(tableName = "Coins", indices = {@Index(value = "symbol", unique = true)})
public class CoinEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imageUrl;
    private String symbol;
    private String fullName;
    private Integer contentCreatedOn;
    private String name;
    private String coinName;
    private String description;
    private String assetTokenStatus;
    private String algorithm;
    private String proofType;
    private String sortOrder;
    private Boolean sponsored;
    private Taxonomy taxonomy;
    private Rating rating;
    private Boolean isTrading;
    private Integer totalCoinsMined;
    private Integer blockNumber;
    private Integer netHashesPerSecond;
    private Integer blockReward;
    private Integer blockTime;
    private String assetLaunchDate;
    private Integer maxSupply;
    private Integer mktCapPenalty;
    private Integer isUsedInDefi;
    private Integer isUsedInNft;

    //unpacker constructor
    public CoinEntity(LinkedHashMap<String, Object> value) {
        imageUrl = (String) value.get("ImageUrl");
        symbol = (String) value.get("Symbol");
        fullName = (String) value.get("FullName");
        contentCreatedOn = (Integer) value.get("ContentCreatedOn");
        name = (String) value.get("Name");
        coinName = (String) value.get("CoinName");
        description = (String) value.get("Description");
        assetTokenStatus = (String) value.get("AssetTokenStatus");
        algorithm = (String) value.get("Algorithm");
        proofType = (String) value.get("ProofOfType");
        sortOrder = (String) value.get("SortOrder");
        sponsored = (Boolean) value.get("Sponsored");
        taxonomy = new Taxonomy(value.get("Taxonomy"));
        rating = (Rating) value.get("Rating");
        isTrading = (Boolean) value.get("IsTrading");
        totalCoinsMined = (Integer) value.get("TotalCoinsMined");
        blockNumber = (Integer) value.get("BlockNumber");
        netHashesPerSecond = (Integer) value.get("NetHashesPerSecond");
        blockReward = (Integer) value.get("BlockReward");
        blockTime = (Integer) value.get("BlockTime");
        assetLaunchDate = (String) value.get("AssetLaunchDate");
        maxSupply = (Integer) value.get("MaxSupply");
        mktCapPenalty = (Integer) value.get("MtkCapPenalty");
        isUsedInDefi = (Integer) value.get("IsUsedInDefi");
        isUsedInNft = (Integer) value.get("IsUsedInNft");
    }

    //room constructor
    public CoinEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
