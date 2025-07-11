package com.fuelup.azs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FuelDto {
    
    @JsonProperty("Id")
    private String id;
    
    @JsonProperty("Price")
    private BigDecimal price;
    
    @JsonProperty("Type")
    private String type;
    
    @JsonProperty("TypeId")
    private Integer typeId;
    
    @JsonProperty("Brand")
    private String brand;
    
    @JsonProperty("Name")
    private String name;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}