package com.fuelup.azs.service.impl;

import com.fuelup.azs.entity.Price;
import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.repository.PriceRepository;
import com.fuelup.azs.repository.FuelDispenserRepository;
import com.fuelup.azs.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class PriceDebugService {
    
    private static final Logger log = LoggerFactory.getLogger(PriceDebugService.class);
    
    private final PriceRepository priceRepo;
    private final FuelDispenserRepository dispenserRepo;
    private final StationRepository stationRepo;
    
    public PriceDebugService() {
        this.priceRepo = new PriceRepository();
        this.dispenserRepo = new FuelDispenserRepository();
        this.stationRepo = new StationRepository();
    }
    
    public void debugPrices() {
        try {
            log.info("=== ОТЛАДКА ЦЕН ===");

            List<Station> stations = stationRepo.findAll();
            log.info("Всего станций в БД: {}", stations.size());

            List<FuelDispenser> dispensers = dispenserRepo.findAll();
            log.info("Всего ТРК в БД: {}", dispensers.size());

            List<Price> prices = priceRepo.findAll();
            log.info("Всего цен в БД: {}", prices.size());

        dispenserRepo.findAll().forEach(dispenser -> {
            try {
                log.info("ТРК {}: тип={}, текущая_цена={}", 
                        dispenser.getExternalId(), 
                        dispenser.getFuelType(),
                        dispenser.getCurrentPrice());
                        
                if (dispenser.getCurrentPrice() != null) {
                    try {
                        Price testPrice = new Price(dispenser.getCurrentPrice(), dispenser);
                        priceRepo.save(testPrice);
                        log.info("Тестовая цена сохранена для ТРК {}", dispenser.getExternalId());
                    } catch (Exception e) {
                        log.error("Ошибка сохранения тестовой цены для ТРК {}: {}", 
                                dispenser.getExternalId(), e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                log.error("Ошибка обработки ТРК {}: {}", dispenser.getExternalId(), e.getMessage());
            }
        });

            prices = priceRepo.findAll();
            log.info("Цен после тестирования: {}", prices.size());
            
        } catch (Exception e) {
            log.error("Критическая ошибка в отладке цен: {}", e.getMessage(), e);
            throw e;
        }
    }
}