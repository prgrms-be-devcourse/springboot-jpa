package com.kdt.customer.domain.repository;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityMangerCallback<T> {
    T execute(EntityManager entityManager);
}
