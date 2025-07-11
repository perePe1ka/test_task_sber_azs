package com.fuelup.azs.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "external_id", unique = true, nullable = false)
    private String externalId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "brand")
    private String brand;
    
    @Column(name = "brand_id")
    private String brandId;
    
    @Column(name = "organization_name")
    private String organizationName;
    
    @Column(name = "organization_inn")
    private String organizationInn;
    
    @Column(name = "organization_kpp")
    private String organizationKpp;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "take_off_mode")
    private String takeOffMode;
    
    @Column(name = "post_pay")
    private Boolean postPay;
    
    @Column(name = "enable")
    private Boolean enable;
    
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FuelDispenser> fuelDispensers;
    
    public Station() {}
    
    public Station(String externalId, String name, String address, String brand, String brandId,
                   String organizationName, String organizationInn, String organizationKpp,
                   Double latitude, Double longitude, String takeOffMode, Boolean postPay, Boolean enable) {
        this.externalId = externalId;
        this.name = name;
        this.address = address;
        this.brand = brand;
        this.brandId = brandId;
        this.organizationName = organizationName;
        this.organizationInn = organizationInn;
        this.organizationKpp = organizationKpp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.takeOffMode = takeOffMode;
        this.postPay = postPay;
        this.enable = enable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getBrandId() { return brandId; }
    public void setBrandId(String brandId) { this.brandId = brandId; }
    
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    
    public String getOrganizationInn() { return organizationInn; }
    public void setOrganizationInn(String organizationInn) { this.organizationInn = organizationInn; }
    
    public String getOrganizationKpp() { return organizationKpp; }
    public void setOrganizationKpp(String organizationKpp) { this.organizationKpp = organizationKpp; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public String getTakeOffMode() { return takeOffMode; }
    public void setTakeOffMode(String takeOffMode) { this.takeOffMode = takeOffMode; }
    
    public Boolean getPostPay() { return postPay; }
    public void setPostPay(Boolean postPay) { this.postPay = postPay; }
    
    public Boolean getEnable() { return enable; }
    public void setEnable(Boolean enable) { this.enable = enable; }
    
    public List<FuelDispenser> getFuelDispensers() { return fuelDispensers; }
    public void setFuelDispensers(List<FuelDispenser> fuelDispensers) { this.fuelDispensers = fuelDispensers; }
}