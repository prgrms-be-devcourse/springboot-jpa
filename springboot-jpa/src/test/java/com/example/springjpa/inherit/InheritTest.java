package com.example.springjpa.inherit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
class InheritTest {

    @Autowired
    EntityManagerFactory emf;


    @Test
    void itemTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Car car = new Car();
        car.setPrice(100);
        car.setStockQuantity(10);
        car.setPower(1000L);
        em.persist(car);

        Food food = new Food();
        food.setPrice(100);
        food.setStockQuantity(10);
        food.setChef("백종원");
        em.persist(food);

        Furniture furniture = new Furniture();
        furniture.setPrice(100);
        furniture.setStockQuantity(10);
        furniture.setHeight(1000);
        furniture.setWidth(2000);
        em.persist(furniture);
        transaction.commit();
    }
}