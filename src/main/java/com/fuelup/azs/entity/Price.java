package com.fuelup.azs.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_dispenser_id")
    private FuelDispenser fuelDispenser;

    public Price() {
    }

    public Price(BigDecimal price, FuelDispenser dispenser) {
        this.price = price;
        this.fuelDispenser = dispenser;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FuelDispenser getFuelDispenser() {
        return fuelDispenser;
    }

    public void setFuelDispenser(FuelDispenser fuelDispenser) {
        this.fuelDispenser = fuelDispenser;
    }
}
