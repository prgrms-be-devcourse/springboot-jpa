package com.example.springbootpart4.domain.order;

import com.example.springbootpart4.domain.order.item.Food;
import com.example.springbootpart4.domain.parent.Parent;
import com.example.springbootpart4.domain.parent.ParentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ImproveMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void inheritance_join_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Food food = new Food();
        food.setPrice(1000);
        food.setStockQuantity(100);
        food.setChef("김소현");

        entityManager.persist(food);

        transaction.commit();
    }

    @Test
    void inheritance_single_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Food food = new Food();
        food.setPrice(2000);
        food.setStockQuantity(200);
        food.setChef("김소현");

        entityManager.persist(food);

        transaction.commit();
    }

    @Test
    void mapped_super_class_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("memo");

        //
        order.setCreatedBy("sohyeon");
        order.setCreatedAt(LocalDateTime.now());

        entityManager.persist(order);

        transaction.commit();
    }

//    @Test
//    void id_test() {    // @IdClass 식별자 클래스 전략
//        EntityManager entityManager = emf.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        Parent parent = new Parent();
//        parent.setId1("id1");
//        parent.setId2("id2");
//
//        entityManager.persist(parent);
//
//        transaction.commit();
//
//        entityManager.clear();
//        Parent findParent = entityManager.find(Parent.class, new ParentId("id1", "id2"));
//        log.info("{} {}", findParent.getId1(), findParent.getId2());
//    }

    @Test
    void id_embedded_test() {    // @IdClass 식별자 클래스 전략
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Parent parent = new Parent();
        parent.setId(new ParentId("id1", "id2"));

        entityManager.persist(parent);

        transaction.commit();

        entityManager.clear();
        Parent findParent = entityManager.find(Parent.class, new ParentId("id1", "id2"));
        log.info("{} {}", findParent.getId().getId1(), findParent.getId().getId2());
    }
}
