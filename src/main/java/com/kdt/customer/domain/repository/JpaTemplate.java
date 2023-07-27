package com.kdt.customer.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaTemplate {
    private final EntityManagerFactory factory;

    public <T> T executeInTransaction(EntityMangerCallback<T> callback) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return executeCallback(entityManager, callback);
        }
    }

    private <T> T executeCallback(EntityManager entityManager, EntityMangerCallback<T> callback) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            return callback.execute(entityManager);
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            transaction.commit();
        }
    }
}
