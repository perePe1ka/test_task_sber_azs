package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDto {
    
    @JsonProperty("Lon")
    private Double longitude;
    
    @JsonProperty("Lat")
    private Double latitude;

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
}