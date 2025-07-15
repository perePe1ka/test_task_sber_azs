package com.fuelup.azs.service;

import com.fuelup.azs.entity.Price;
import com.fuelup.azs.entity.FuelDispenser;
import java.util.List;

public interface PriceService {
    List<Price> getAllPrices();
}