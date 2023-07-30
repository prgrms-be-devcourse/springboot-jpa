package com.jpaweekily.domain.customer.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LifeCycleTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void test() {
        EntityManager entityManager = emf.createEntityManager();

    }

}
