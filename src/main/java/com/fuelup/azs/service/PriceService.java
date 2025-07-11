package com.fuelup.azs.service;

import com.fuelup.azs.entity.Price;
import com.fuelup.azs.entity.FuelDispenser;
import java.util.List;

public interface PriceService {
    void loadAndSavePrices();
    List<Price> getAllPrices();
    List<Price> getPricesByFuelDispenser(FuelDispenser fuelDispenser);
}