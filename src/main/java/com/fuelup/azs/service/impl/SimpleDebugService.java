package com.fuelup.azs.service.impl;

import com.fuelup.azs.repository.PriceRepository;
import com.fuelup.azs.repository.FuelDispenserRepository;
import com.fuelup.azs.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDebugService {
    
    private static final Logger log = LoggerFactory.getLogger(SimpleDebugService.class);
    
    private final PriceRepository priceRepo;
    private final FuelDispenserRepository dispenserRepo;
    private final StationRepository stationRepo;
    
    public SimpleDebugService() {
        this.priceRepo = new PriceRepository();
        this.dispenserRepo = new FuelDispenserRepository();
        this.stationRepo = new StationRepository();
    }
    
    public void simpleDebug() {
        try {
            log.info("=== ПРОСТАЯ ОТЛАДКА ===");

            int stationsCount = stationRepo.findAll().size();
            log.info("Всего станций в БД: {}", stationsCount);
            
            int dispensersCount = dispenserRepo.findAll().size();
            log.info("Всего ТРК в БД: {}", dispensersCount);
            
            int pricesCount = priceRepo.findAll().size();
            log.info("Всего цен в БД: {}", pricesCount);
            
            log.info("=== ОТЛАДКА ЗАВЕРШЕНА ===");
            
        } catch (Exception e) {
            log.error("Ошибка в простой отладке: {}", e.getMessage(), e);
            throw e;
        }
    }
}