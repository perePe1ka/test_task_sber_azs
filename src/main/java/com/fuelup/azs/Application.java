package com.fuelup.azs;

import com.fuelup.azs.controller.FuelSystemController;
import com.fuelup.azs.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        logger.info("Запуск приложения АЗС Fuel System");
        
        FuelSystemController controller = new FuelSystemController();
        
        try {
            controller.start();
        } catch (Exception e) {
            logger.error("Критическая ошибка в приложении", e);
        } finally {
            controller.shutdown();
            HibernateUtil.shutdown();
            logger.info("Приложение завершено");
        }
    }
}