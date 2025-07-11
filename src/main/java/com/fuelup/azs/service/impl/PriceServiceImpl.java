package com.fuelup.azs.service.impl;

import com.fuelup.azs.dto.FuelDto;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Price;
import com.fuelup.azs.repository.StationRepository;
import com.fuelup.azs.repository.FuelDispenserRepository;
import com.fuelup.azs.repository.PriceRepository;
import com.fuelup.azs.service.ApiService;
import com.fuelup.azs.service.PriceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PriceServiceImpl implements PriceService {
    
    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    
    private final ApiService apiService;
    private final StationRepository stationRepository;
    private final FuelDispenserRepository fuelDispenserRepository;
    private final PriceRepository priceRepository;
    private final ObjectMapper objectMapper;
    
    public PriceServiceImpl() {
        this.apiService = new ApiService();
        this.stationRepository = new StationRepository();
        this.fuelDispenserRepository = new FuelDispenserRepository();
        this.priceRepository = new PriceRepository();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void loadAndSavePrices() {
        logger.info("Цены загружаются вместе со станциями. Используйте загрузку станций для получения актуальных цен.");
    }
    
    
    @Override
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }
    
    @Override
    public List<Price> getPricesByFuelDispenser(FuelDispenser fuelDispenser) {
        return priceRepository.findByFuelDispenser(fuelDispenser);
    }
}