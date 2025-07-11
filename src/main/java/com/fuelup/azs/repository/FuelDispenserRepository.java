package com.fuelup.azs.repository;

import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FuelDispenserRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(FuelDispenserRepository.class);
    
    public void save(FuelDispenser fuelDispenser) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(fuelDispenser);
            transaction.commit();
            logger.debug("ТРК сохранена: {}", fuelDispenser.getExternalId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка сохранения ТРК: {}", fuelDispenser.getExternalId(), e);
            throw e;
        }
    }
    
    public Optional<FuelDispenser> findByExternalIdAndStation(String externalId, Station station) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            FuelDispenser dispenser = session.createQuery(
                    "FROM FuelDispenser WHERE externalId = :externalId AND station = :station", 
                    FuelDispenser.class)
                    .setParameter("externalId", externalId)
                    .setParameter("station", station)
                    .uniqueResult();
            return Optional.ofNullable(dispenser);
        } catch (Exception e) {
            logger.error("Ошибка поиска ТРК по external_id: {} и станции: {}", externalId, station.getExternalId(), e);
            throw e;
        }
    }
    
    public List<FuelDispenser> findByStation(Station station) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM FuelDispenser WHERE station = :station", FuelDispenser.class)
                    .setParameter("station", station)
                    .list();
        } catch (Exception e) {
            logger.error("Ошибка получения ТРК для станции: {}", station.getExternalId(), e);
            throw e;
        }
    }
    
    public List<FuelDispenser> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM FuelDispenser fd JOIN FETCH fd.station", FuelDispenser.class).list();
        } catch (Exception e) {
            logger.error("Ошибка получения всех ТРК", e);
            throw e;
        }
    }
}