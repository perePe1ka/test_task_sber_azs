package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PriceDto {

    @JsonProperty("StationId")
    private String stationId;
    @JsonProperty("FuelId")
    private String fuelId;
    @JsonProperty("Price")
    private BigDecimal price;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getFuelId() {
        return fuelId;
    }

    public void setFuelId(String fuelId) {
        this.fuelId = fuelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}