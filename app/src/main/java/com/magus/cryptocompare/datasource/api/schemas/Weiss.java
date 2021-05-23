
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
        "Rating",
        "TechnologyAdoptionRating",
        "MarketPerformanceRating"
})
public class Weiss {

    @JsonProperty("Rating")
    private String rating;
    @JsonProperty("TechnologyAdoptionRating")
    private String technologyAdoptionRating;
    @JsonProperty("MarketPerformanceRating")
    private String marketPerformanceRating;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Rating")
    public String getRating() {
        return rating;
    }

    @JsonProperty("Rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @JsonProperty("TechnologyAdoptionRating")
    public String getTechnologyAdoptionRating() {
        return technologyAdoptionRating;
    }

    @JsonProperty("TechnologyAdoptionRating")
    public void setTechnologyAdoptionRating(String technologyAdoptionRating) {
        this.technologyAdoptionRating = technologyAdoptionRating;
    }

    @JsonProperty("MarketPerformanceRating")
    public String getMarketPerformanceRating() {
        return marketPerformanceRating;
    }

    @JsonProperty("MarketPerformanceRating")
    public void setMarketPerformanceRating(String marketPerformanceRating) {
        this.marketPerformanceRating = marketPerformanceRating;
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
