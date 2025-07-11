package com.fuelup.azs.util;

import com.fuelup.azs.config.ConfigLoader;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Price;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HibernateUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", ConfigLoader.getDatabaseDriver());
            properties.setProperty("hibernate.connection.url", ConfigLoader.getDatabaseUrl());
            properties.setProperty("hibernate.connection.username", ConfigLoader.getDatabaseUsername());
            properties.setProperty("hibernate.connection.password", ConfigLoader.getDatabasePassword());
            properties.setProperty("hibernate.dialect", ConfigLoader.getHibernateDialect());
            properties.setProperty("hibernate.hbm2ddl.auto", ConfigLoader.getHibernateHbm2ddlAuto());
            properties.setProperty("hibernate.show_sql", ConfigLoader.getHibernateShowSql().toString());
            properties.setProperty("hibernate.format_sql", ConfigLoader.getHibernateFormatSql().toString());
            
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Station.class);
            configuration.addAnnotatedClass(FuelDispenser.class);
            configuration.addAnnotatedClass(Price.class);
            
            sessionFactory = configuration.buildSessionFactory();
            logger.info("Hibernate SessionFactory создана успешно");
        } catch (Exception e) {
            logger.error("Ошибка создания SessionFactory", e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            logger.info("Hibernate SessionFactory закрыта");
        }
    }
}