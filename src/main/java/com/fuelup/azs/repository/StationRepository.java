package com.fuelup.azs.repository;

import com.fuelup.azs.entity.Station;
import com.fuelup.azs.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class StationRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(StationRepository.class);
    
    public void save(Station station) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(station);
            transaction.commit();
            logger.debug("Станция сохранена: {}", station.getExternalId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка сохранения станции: {}", station.getExternalId(), e);
            throw e;
        }
    }
    
    public Optional<Station> findByExternalId(String externalId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Station station = session.createQuery("FROM Station WHERE externalId = :externalId", Station.class)
                    .setParameter("externalId", externalId)
                    .uniqueResult();
            return Optional.ofNullable(station);
        } catch (Exception e) {
            logger.error("Ошибка поиска станции по external_id: {}", externalId, e);
            throw e;
        }
    }
    
    public List<Station> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Station", Station.class).list();
        } catch (Exception e) {
            logger.error("Ошибка получения всех станций", e);
            throw e;
        }
    }
    
    public List<Station> findActiveStations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Station WHERE enable = true", Station.class).list();
        } catch (Exception e) {
            logger.error("Ошибка получения активных станций", e);
            throw e;
        }
    }
}