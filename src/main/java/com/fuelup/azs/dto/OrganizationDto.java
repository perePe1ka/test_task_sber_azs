package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrganizationDto {
    
    @JsonProperty("Name")
    private String name;
    
    @JsonProperty("Inn")
    private String inn;
    
    @JsonProperty("Kpp")
    private String kpp;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getInn() { return inn; }
    public void setInn(String inn) { this.inn = inn; }
    
    public String getKpp() { return kpp; }
    public void setKpp(String kpp) { this.kpp = kpp; }
}