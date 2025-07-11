package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ColumnDto {

    @JsonProperty("Fuels")
    private List<String> fuelIds;

    public List<String> getFuelIds() {
        return fuelIds;
    }

    public void setFuelIds(List<String> fuelIds) {
        this.fuelIds = fuelIds;
    }
}