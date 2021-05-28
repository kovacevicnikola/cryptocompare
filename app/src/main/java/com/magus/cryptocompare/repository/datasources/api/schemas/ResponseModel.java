
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
        "Response",
        "Message",
        "HasWarning",
        "Type",
        "RateLimit",
        "Data"
})
public class ResponseModel {

    @JsonProperty("Response")
    private String response;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("HasWarning")
    private Boolean hasWarning;
    @JsonProperty("Type")
    private Integer type;
    @JsonProperty("RateLimit")
    private RateLimitSchema rateLimit;
    @JsonProperty("Data")
    private BodySchema data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("HasWarning")
    public Boolean getHasWarning() {
        return hasWarning;
    }

    @JsonProperty("HasWarning")
    public void setHasWarning(Boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    @JsonProperty("Type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("RateLimit")
    public RateLimitSchema getRateLimit() {
        return rateLimit;
    }

    @JsonProperty("RateLimit")
    public void setRateLimit(RateLimitSchema rateLimit) {
        this.rateLimit = rateLimit;
    }

    @JsonProperty("Data")
    public BodySchema getData() {
        return data;
    }

    @JsonProperty("Data")
    public void setData(BodySchema data) {
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
