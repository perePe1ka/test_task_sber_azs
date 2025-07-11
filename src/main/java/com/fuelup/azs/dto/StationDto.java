package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class StationDto {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Brand")
    private String brand;
    @JsonProperty("BrandId")
    private String brandId;

    @JsonProperty("Organization")
    private OrganizationDto organization;
    @JsonProperty("Location")
    private LocationDto location;

    @JsonProperty("TakeOffMode")
    private String takeOffMode;
    @JsonProperty("PostPay")
    private boolean postPay;
    @JsonProperty("Enable")
    private boolean enable;

    @JsonProperty("Fuels")
    private List<FuelDto> fuels;

    @JsonProperty("Columns")
    private Map<String, ColumnDto> columns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public String getTakeOffMode() {
        return takeOffMode;
    }

    public void setTakeOffMode(String takeOffMode) {
        this.takeOffMode = takeOffMode;
    }

    public Boolean getPostPay() {
        return postPay;
    }

    public void setPostPay(Boolean postPay) {
        this.postPay = postPay;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<FuelDto> getFuels() {
        return fuels;
    }

    public void setFuels(List<FuelDto> fuels) {
        this.fuels = fuels;
    }
}