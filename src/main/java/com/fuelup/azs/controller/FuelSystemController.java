package com.fuelup.azs.controller;

import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.entity.Price;
import com.fuelup.azs.service.StationService;
import com.fuelup.azs.service.PriceService;
import com.fuelup.azs.service.impl.StationServiceImpl;
import com.fuelup.azs.service.impl.PriceServiceImpl;
import com.fuelup.azs.service.impl.PriceDebugService;
import com.fuelup.azs.service.impl.SimpleDebugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class FuelSystemController {
    
    private static final Logger logger = LoggerFactory.getLogger(FuelSystemController.class);
    
    private final StationService stationService;
    private final PriceService priceService;
    private final PriceDebugService debugService;
    private final SimpleDebugService simpleDebugService;
    private final Scanner scanner;
    
    public FuelSystemController() {
        this.stationService = new StationServiceImpl();
        this.priceService = new PriceServiceImpl();
        this.debugService = new PriceDebugService();
        this.simpleDebugService = new SimpleDebugService();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        logger.info("Запуск системы управления АЗС");
        
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    loadStations();
                    break;
                case "2":
                    showAllStations();
                    break;
                case "3":
                    showAllPrices();
                    break;
                case "4":
                    debugPrices();
                    break;
                case "0":
                    logger.info("Завершение работы системы");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
    
    private void printMenu() {
        System.out.println("\n=== СИСТЕМА УПРАВЛЕНИЯ АЗС ===");
        System.out.println("1. Загрузить данные");
        System.out.println("2. Показать все станции");
        System.out.println("3. Показать все цены");
        System.out.println("4. Отладка цен");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }
    
    private void loadStations() {
        try {
            System.out.println("Загрузка данных о станциях...");
            stationService.loadAndSaveStations();
            System.out.println("Данные о станциях успешно загружены!");
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке станций: " + e.getMessage());
            logger.error("Ошибка загрузки станций", e);
        }
    }
    
    private void showAllStations() {
        try {
            List<Station> stations = stationService.getAllStations();
            System.out.println("\n=== ВСЕ СТАНЦИИ ===");
            System.out.println("Всего станций: " + stations.size());
            for (Station station : stations) {
                System.out.printf("ID: %s, Название: %s, Адрес: %s, Бренд: %s, Активна: %s%n",
                        station.getExternalId(), station.getName(), station.getAddress(), 
                        station.getBrand(), station.getEnable());
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении станций: " + e.getMessage());
            logger.error("Ошибка получения станций", e);
        }
    }
    
    private void showAllPrices() {
        try {
            List<Price> prices = priceService.getAllPrices();
            System.out.println("\n=== ВСЕ ЦЕНЫ ===");
            System.out.println("Всего записей о ценах: " + prices.size());
            
            if (prices.isEmpty()) {
                System.out.println("Цены не найдены. Сначала загрузите данные (пункт 1).");
                return;
            }
            
            for (Price price : prices) {
                try {
                    FuelDispenser dispenser = price.getFuelDispenser();
                    Station station = dispenser.getStation();
                    System.out.printf("Цена: %s руб, ТРК: %s (%s %s), Станция: %s (%s), Дата: %s%n",
                            price.getPrice(),
                            dispenser.getExternalId(),
                            dispenser.getFuelType(),
                            dispenser.getFuelName() != null ? dispenser.getFuelName() : "",
                            station.getExternalId(),
                            station.getName() != null ? station.getName() : "Без названия",
                            price.getCreatedAt().toString().substring(0, 19));
                } catch (Exception e) {
                    System.out.printf("Ошибка отображения цены ID %s: %s%n", price.getId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении цен: " + e.getMessage());
            logger.error("Ошибка получения цен", e);
        }
    }
    
    private void debugPrices() {
        System.out.println("Запуск отладки цен...");

        try {
            System.out.println("Выполняется простая отладка...");
            simpleDebugService.simpleDebug();
            System.out.println("Простая отладка завершена успешно.");
        } catch (Exception e) {
            System.out.println("Ошибка простой отладки: " + e.getMessage());
            logger.error("Ошибка простой отладки", e);
            return;
        }

        try {
            System.out.println("Выполняется полная отладка...");
            debugService.debugPrices();
            System.out.println("Полная отладка завершена. Проверьте логи для подробностей.");
        } catch (Exception e) {
            System.out.println("Ошибка полной отладки: " + e.getMessage());
            System.out.println("Тип ошибки: " + e.getClass().getSimpleName());
            logger.error("Ошибка полной отладки", e);
            System.out.println("Простая отладка прошла успешно, проблема в полной отладке.");
        }
    }
    
    public void shutdown() {
        scanner.close();
    }
}