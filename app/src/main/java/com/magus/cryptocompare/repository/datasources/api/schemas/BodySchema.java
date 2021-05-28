
package com.magus.cryptocompare.repository.datasources.api.schemas;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Aggregated",
        "TimeFrom",
        "TimeTo",
        "Data"
})
public class BodySchema {

    @JsonProperty("Aggregated")
    private Boolean aggregated;
    @JsonProperty("TimeFrom")
    private Integer timeFrom;
    @JsonProperty("TimeTo")
    private Integer timeTo;
    @JsonProperty("Data")
    private List<PriceAndVolumeSchema> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Aggregated")
    public Boolean getAggregated() {
        return aggregated;
    }

    @JsonProperty("Aggregated")
    public void setAggregated(Boolean aggregated) {
        this.aggregated = aggregated;
    }

    @JsonProperty("TimeFrom")
    public Integer getTimeFrom() {
        return timeFrom;
    }

    @JsonProperty("TimeFrom")
    public void setTimeFrom(Integer timeFrom) {
        this.timeFrom = timeFrom;
    }

    @JsonProperty("TimeTo")
    public Integer getTimeTo() {
        return timeTo;
    }

    @JsonProperty("TimeTo")
    public void setTimeTo(Integer timeTo) {
        this.timeTo = timeTo;
    }

    @JsonProperty("Data")
    public List<PriceAndVolumeSchema> getData() {
        return data;
    }

    @JsonProperty("Data")
    public void setData(List<PriceAndVolumeSchema> data) {
        this.data = data;
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
