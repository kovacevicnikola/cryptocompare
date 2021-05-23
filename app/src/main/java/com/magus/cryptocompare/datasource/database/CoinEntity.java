package com.magus.cryptocompare.datasource.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.magus.cryptocompare.pojo.StringUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.Locale;

import timber.log.Timber;

@Entity(tableName = "Coins", indices = {@Index(value = "symbol", unique = true)})
public class CoinEntity implements Parcelable {
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
    private Boolean isTrading;
    public static final Creator<CoinEntity> CREATOR = new Creator<CoinEntity>() {
        @Override
        public CoinEntity createFromParcel(Parcel in) {
            return new CoinEntity(in);
        }

        @Override
        public CoinEntity[] newArray(int size) {
            return new CoinEntity[size];
        }
    };
    private Integer blockNumber;
    private Double totalCoinsMined;
    private Double netHashesPerSecond;
    private Double blockReward;
    private String assetLaunchDate;
    private Double blockTime;
    private Double maxSupply;
    private Double mktCapPenalty;
    private Double isUsedInDefi;
    private Double isUsedInNft;
    private String rating;
    private String technologyAdoptionRating;
    private String marketPerformanceRating;
    private String access;
    private String fca;
    private String finma;
    private String industry;
    private String collateralizedAsset;
    private String collateralizedAssetType;
    private String collateralType;
    private String collateralInfo;
    @Ignore
    private LinkedHashMap<String, String> labelValueHashMap;

    //unpacker constructor
    protected CoinEntity(Parcel in) {
        id = in.readInt();
        imageUrl = in.readString();
        symbol = in.readString();
        fullName = in.readString();
        if (in.readByte() == 0) {
            contentCreatedOn = null;
        } else {
            contentCreatedOn = in.readInt();
        }
        name = in.readString();
        coinName = in.readString();
        description = in.readString();
        assetTokenStatus = in.readString();
        algorithm = in.readString();
        proofType = in.readString();
        sortOrder = in.readString();
        byte tmpSponsored = in.readByte();
        sponsored = tmpSponsored == 0 ? null : tmpSponsored == 1;
        byte tmpIsTrading = in.readByte();
        isTrading = tmpIsTrading == 0 ? null : tmpIsTrading == 1;
        if (in.readByte() == 0) {
            totalCoinsMined = null;
        } else {
            totalCoinsMined = in.readDouble();
        }
        if (in.readByte() == 0) {
            blockNumber = null;
        } else {
            blockNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            netHashesPerSecond = null;
        } else {
            netHashesPerSecond = in.readDouble();
        }
        if (in.readByte() == 0) {
            blockReward = null;
        } else {
            blockReward = in.readDouble();
        }
        if (in.readByte() == 0) {
            blockTime = null;
        } else {
            blockTime = in.readDouble();
        }
        assetLaunchDate = in.readString();
        if (in.readByte() == 0) {
            maxSupply = null;
        } else {
            maxSupply = in.readDouble();
        }
        if (in.readByte() == 0) {
            mktCapPenalty = null;
        } else {
            mktCapPenalty = in.readDouble();
        }
        if (in.readByte() == 0) {
            isUsedInDefi = null;
        } else {
            isUsedInDefi = in.readDouble();
        }
        if (in.readByte() == 0) {
            isUsedInNft = null;
        } else {
            isUsedInNft = in.readDouble();
        }
        rating = in.readString();
        technologyAdoptionRating = in.readString();
        marketPerformanceRating = in.readString();
        access = in.readString();
        fca = in.readString();
        finma = in.readString();
        industry = in.readString();
        collateralizedAsset = in.readString();
        collateralizedAssetType = in.readString();
        collateralType = in.readString();
        collateralInfo = in.readString();
    }

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
        proofType = (String) value.get("ProofType");
        sortOrder = (String) value.get("SortOrder");
        sponsored = (Boolean) value.get("Sponsored");
        isTrading = (Boolean) value.get("IsTrading");
        totalCoinsMined = parseDouble(value.get("TotalCoinsMined"));
        blockNumber = (Integer) value.get("BlockNumber");
        netHashesPerSecond = parseDouble(value.get("NetHashesPerSecond"));
        blockReward = parseDouble(value.get("BlockReward"));
        blockTime = parseDouble(value.get("BlockTime"));
        assetLaunchDate = (String) value.get("AssetLaunchDate");
        maxSupply = parseDouble(value.get("MaxSupply"));
        mktCapPenalty = parseDouble(value.get("MtkCapPenalty"));
        isUsedInDefi = parseDouble(value.get("IsUsedInDefi"));
        isUsedInNft = parseDouble(value.get("IsUsedInNft"));
        //unpacking & flattening for db
        rating = ((LinkedHashMap<String, String>) ((LinkedHashMap<String, Object>) value.get("Rating")).get("Weiss")).get("Rating");
        technologyAdoptionRating = ((LinkedHashMap<String, String>) ((LinkedHashMap<String, Object>) value.get("Rating")).get("Weiss")).get("TechnologyAdoptionRating");
        marketPerformanceRating = ((LinkedHashMap<String, String>) ((LinkedHashMap<String, Object>) value.get("Rating")).get("Weiss")).get("MarketPerformanceRating");
        access = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("Access");
        fca = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("FCA");
        finma = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("FINMA");
        industry = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("Industry");
        collateralizedAsset = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("CollateralizedAsset");
        collateralizedAssetType = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("CollateralizedAssetType");
        collateralType = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("CollateralType");
        collateralInfo = ((LinkedHashMap<String, String>) value.get("Taxonomy")).get("CollateralInfo");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(symbol);
        dest.writeString(fullName);
        if (contentCreatedOn == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(contentCreatedOn);
        }
        dest.writeString(name);
        dest.writeString(coinName);
        dest.writeString(description);
        dest.writeString(assetTokenStatus);
        dest.writeString(algorithm);
        dest.writeString(proofType);
        dest.writeString(sortOrder);
        dest.writeByte((byte) (sponsored == null ? 0 : sponsored ? 1 : 2));
        dest.writeByte((byte) (isTrading == null ? 0 : isTrading ? 1 : 2));
        if (totalCoinsMined == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalCoinsMined);
        }
        if (blockNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(blockNumber);
        }
        if (netHashesPerSecond == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(netHashesPerSecond);
        }
        if (blockReward == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(blockReward);
        }
        if (blockTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(blockTime);
        }
        dest.writeString(assetLaunchDate);
        if (maxSupply == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(maxSupply);
        }
        if (mktCapPenalty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mktCapPenalty);
        }
        if (isUsedInDefi == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(isUsedInDefi);
        }
        if (isUsedInNft == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(isUsedInNft);
        }
        dest.writeString(rating);
        dest.writeString(technologyAdoptionRating);
        dest.writeString(marketPerformanceRating);
        dest.writeString(access);
        dest.writeString(fca);
        dest.writeString(finma);
        dest.writeString(industry);
        dest.writeString(collateralizedAsset);
        dest.writeString(collateralizedAssetType);
        dest.writeString(collateralType);
        dest.writeString(collateralInfo);
    }

    private Double parseDouble(Object object) {
        try {
            if (object == null) return null;
            if (object instanceof Double) return ((Double) object).doubleValue();
            if (object instanceof Integer) return ((Integer) object).doubleValue();
            if (object instanceof String) return Double.parseDouble((String) object);
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
        return null;
    }

    public LinkedHashMap<String, String> getLabelValueHashMap() {
        if (labelValueHashMap == null) mapVariablesToHashMap();
        return labelValueHashMap;
    }

    private void mapVariablesToHashMap() {
        labelValueHashMap = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(symbol)) labelValueHashMap.put("Symbol", symbol);
        if (StringUtils.isNotBlank(name)) labelValueHashMap.put("Name", name);

        if (StringUtils.isNotBlank(fullName)) labelValueHashMap.put("Full name", fullName);
        if (StringUtils.isNotBlank(coinName)) labelValueHashMap.put("Coin name", coinName);

        if (contentCreatedOn != null) labelValueHashMap.put("Content created on",
                LocalDateTime.ofInstant(Instant.ofEpochSecond(contentCreatedOn), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy.").withLocale(Locale.getDefault())));
        if ((StringUtils.isNotBlank(description)))
            labelValueHashMap.put("Description", description);
        if ((StringUtils.isNotBlank(assetTokenStatus)))
            labelValueHashMap.put("Asset Token Status", assetTokenStatus);
        if ((StringUtils.isNotBlank(algorithm))) labelValueHashMap.put("Algorithm", algorithm);
        if ((StringUtils.isNotBlank(proofType))) labelValueHashMap.put("Proof Type", proofType);
        if (sponsored != null) labelValueHashMap.put("Sponsored", sponsored ? "Yes" : "No");
        if (isTrading != null) labelValueHashMap.put("Trading", isTrading ? "Yes" : "No");
        if (totalCoinsMined != null)
            labelValueHashMap.put("Total Coins Mined", String.valueOf(totalCoinsMined));
        if (blockNumber != null) labelValueHashMap.put("Block Number", String.valueOf(blockNumber));
        if (netHashesPerSecond != null)
            labelValueHashMap.put("Net Hashes Per Second", String.valueOf(netHashesPerSecond));
        if (blockReward != null) labelValueHashMap.put("Block Reward", String.valueOf(blockReward));
        if (blockTime != null) labelValueHashMap.put("Block Time", String.valueOf(blockTime));
        if (assetLaunchDate != null) labelValueHashMap.put("Asset launch Date", assetLaunchDate);
        if (maxSupply != null) labelValueHashMap.put("Max supply", String.valueOf(maxSupply));
        if (mktCapPenalty != null)
            labelValueHashMap.put("MTK Cap Penalty", String.valueOf(mktCapPenalty));
        if (isUsedInDefi != null)
            labelValueHashMap.put("Used In DEFI", String.valueOf(isUsedInDefi));
        if (isUsedInNft != null) labelValueHashMap.put("Used In NFT", String.valueOf(isUsedInNft));
        if ((StringUtils.isNotBlank(rating))) labelValueHashMap.put("Rating", rating);
        if ((StringUtils.isNotBlank(technologyAdoptionRating)))
            labelValueHashMap.put("Technology Adoption Rating", technologyAdoptionRating);
        if ((StringUtils.isNotBlank(marketPerformanceRating)))
            labelValueHashMap.put("Market performance rating", marketPerformanceRating);
        if ((StringUtils.isNotBlank(access))) labelValueHashMap.put("Access", access);
        if ((StringUtils.isNotBlank(fca))) labelValueHashMap.put("FCA", fca);
        if ((StringUtils.isNotBlank(finma))) labelValueHashMap.put("FINMA", finma);
        if ((StringUtils.isNotBlank(industry))) labelValueHashMap.put("Industry", industry);
        if ((StringUtils.isNotBlank(collateralizedAsset)))
            labelValueHashMap.put("Collateralized Asset", collateralizedAsset);
        if ((StringUtils.isNotBlank(collateralizedAssetType)))
            labelValueHashMap.put("Collateralized Asset Type", collateralizedAssetType);
        if ((StringUtils.isNotBlank(collateralType)))
            labelValueHashMap.put("Collateral Type", collateralType);
        if ((StringUtils.isNotBlank(collateralInfo)))
            labelValueHashMap.put("Collateral info", collateralInfo);
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

    public Integer getContentCreatedOn() {
        return contentCreatedOn;
    }

    public void setContentCreatedOn(Integer contentCreatedOn) {
        this.contentCreatedOn = contentCreatedOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetTokenStatus() {
        return assetTokenStatus;
    }

    public void setAssetTokenStatus(String assetTokenStatus) {
        this.assetTokenStatus = assetTokenStatus;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getSponsored() {
        return sponsored;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Boolean getTrading() {
        return isTrading;
    }

    public void setTrading(Boolean trading) {
        isTrading = trading;
    }

    public Double getTotalCoinsMined() {
        return totalCoinsMined;
    }

    public void setTotalCoinsMined(Double totalCoinsMined) {
        this.totalCoinsMined = totalCoinsMined;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Double getNetHashesPerSecond() {
        return netHashesPerSecond;
    }

    public void setNetHashesPerSecond(Double netHashesPerSecond) {
        this.netHashesPerSecond = netHashesPerSecond;
    }

    public Double getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(Double blockReward) {
        this.blockReward = blockReward;
    }

    public Double getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Double blockTime) {
        this.blockTime = blockTime;
    }

    public String getAssetLaunchDate() {
        return assetLaunchDate;
    }

    public void setAssetLaunchDate(String assetLaunchDate) {
        this.assetLaunchDate = assetLaunchDate;
    }

    public Double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public Double getMktCapPenalty() {
        return mktCapPenalty;
    }

    public void setMktCapPenalty(Double mktCapPenalty) {
        this.mktCapPenalty = mktCapPenalty;
    }

    public Double getIsUsedInDefi() {
        return isUsedInDefi;
    }

    public void setIsUsedInDefi(Double isUsedInDefi) {
        this.isUsedInDefi = isUsedInDefi;
    }

    public Double getIsUsedInNft() {
        return isUsedInNft;
    }

    public void setIsUsedInNft(Double isUsedInNft) {
        this.isUsedInNft = isUsedInNft;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTechnologyAdoptionRating() {
        return technologyAdoptionRating;
    }

    public void setTechnologyAdoptionRating(String technologyAdoptionRating) {
        this.technologyAdoptionRating = technologyAdoptionRating;
    }

    public String getMarketPerformanceRating() {
        return marketPerformanceRating;
    }

    public void setMarketPerformanceRating(String marketPerformanceRating) {
        this.marketPerformanceRating = marketPerformanceRating;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getFca() {
        return fca;
    }

    public void setFca(String fca) {
        this.fca = fca;
    }

    public String getFinma() {
        return finma;
    }

    public void setFinma(String finma) {
        this.finma = finma;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCollateralizedAsset() {
        return collateralizedAsset;
    }

    public void setCollateralizedAsset(String collateralizedAsset) {
        this.collateralizedAsset = collateralizedAsset;
    }

    public String getCollateralizedAssetType() {
        return collateralizedAssetType;
    }

    public void setCollateralizedAssetType(String collateralizedAssetType) {
        this.collateralizedAssetType = collateralizedAssetType;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getCollateralInfo() {
        return collateralInfo;
    }

    public void setCollateralInfo(String collateralInfo) {
        this.collateralInfo = collateralInfo;
    }
}
