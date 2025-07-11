package com.fuelup.azs.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fuel_dispensers")
public class FuelDispenser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    private String fuelType;
    private Integer fuelTypeId;
    private String fuelBrand;
    private String fuelName;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    private String columnNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToMany(mappedBy = "fuelDispenser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> pricesHistory = new ArrayList<>();

    public FuelDispenser(String externalId,
                         String columnNumber,
                         Integer fuelTypeId,
                         String fuelType,
                         String fuelBrand,
                         String fuelName,
                         BigDecimal currentPrice,
                         Station station) {

        this.externalId   = externalId;
        this.columnNumber = columnNumber;
        this.fuelTypeId   = fuelTypeId;
        this.fuelType     = fuelType;
        this.fuelBrand    = fuelBrand;
        this.fuelName     = fuelName;
        this.currentPrice = currentPrice;
        this.station      = station;
    }

    public FuelDispenser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getFuelBrand() {
        return fuelBrand;
    }

    public void setFuelBrand(String fuelBrand) {
        this.fuelBrand = fuelBrand;
    }

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Price> getPricesHistory() {
        return pricesHistory;
    }

    public void setPricesHistory(List<Price> pricesHistory) {
        this.pricesHistory = pricesHistory;
    }

}
