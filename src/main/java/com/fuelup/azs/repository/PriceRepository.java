package com.fuelup.azs.repository;

import com.fuelup.azs.entity.Price;
import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PriceRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(PriceRepository.class);
    
    public void save(Price price) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            FuelDispenser managedDispenser = session.merge(price.getFuelDispenser());
            price.setFuelDispenser(managedDispenser);
            
            session.save(price);
            transaction.commit();
            logger.info("Цена сохранена: {} для ТРК: {}", price.getPrice(), price.getFuelDispenser().getExternalId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка сохранения цены: {} для ТРК: {}", 
                    price.getPrice(), 
                    price.getFuelDispenser() != null ? price.getFuelDispenser().getExternalId() : "null", e);
            throw e;
        }
    }
    
    public List<Price> findByFuelDispenser(FuelDispenser fuelDispenser) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Price WHERE fuelDispenser = :fuelDispenser ORDER BY createdAt DESC", Price.class)
                    .setParameter("fuelDispenser", fuelDispenser)
                    .list();
        } catch (Exception e) {
            logger.error("Ошибка получения цен для ТРК: {}", fuelDispenser.getExternalId(), e);
            throw e;
        }
    }
    
    public List<Price> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Price p JOIN FETCH p.fuelDispenser fd JOIN FETCH fd.station ORDER BY p.createdAt DESC", Price.class).list();
        } catch (Exception e) {
            logger.error("Ошибка получения всех цен", e);
            throw e;
        }
    }
}