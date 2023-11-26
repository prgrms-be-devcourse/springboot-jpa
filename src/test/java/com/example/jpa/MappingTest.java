package com.example.jpa;

import com.example.jpa.customer.Customer;

import com.example.jpa.orderItem.Item;
import com.example.jpa.orderItem.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class MappingTest {

    private static final Logger log = LoggerFactory.getLogger(MappingTest.class);

    @Autowired
    EntityManagerFactory emf;

    static Customer customer;

    static OrderItem orderItem;
    static Item item;
    @BeforeEach
    void setUp(){
        customer = new Customer();
        customer.setNickname("nickname");
        customer.setName("name");
        customer.setPassword("password");

        orderItem = new OrderItem();

        item = new Item();
        item.setPrice(1000);
        item.setStockQuantity(10);
        item.setItemName("item");

    }

    @Test
    @DisplayName("연관관계 매핑")
    void getMapping(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(orderItem);
        em.persist(item);
        em.flush();
        em.clear();
        orderItem.setItems(List.of(item));
        log.info("{}", orderItem);
        log.info("{}", item);
        transaction.commit();
        em.close();
    }

}
