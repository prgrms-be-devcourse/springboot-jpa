package com.part4.jpa2;

import com.part4.jpa2.domain.order.Food;
import com.part4.jpa2.domain.order.Order;
import com.part4.jpa2.domain.order.OrderStatus;
import com.part4.jpa2.domain.parent.embeddedid.ParentIdv2;
import com.part4.jpa2.domain.parent.embeddedid.Parentv2;
import com.part4.jpa2.domain.parent.idclass.Parentv1;
import com.part4.jpa2.domain.parent.idclass.ParentIdv1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
@DisplayName("3-5 고급매핑")
public class 고급매핑 {

    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp(){
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @Test
    void 상속테이블_관리하기_Inheritance(){
        transaction.begin();
        Food food = new Food();
        food.setPrice(1000);
        food.setStockQuantity(100);
        food.setChef("백종원");
        em.persist(food);

        transaction.commit();
    }

    @Test
    void Entity_공통속성_만들기_MappedSuperclass(){
        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("---");
        order.setOrderDatetime(LocalDateTime.now());

        order.setCreatedBy("subin.kim");
        order.setCreatedAt(LocalDateTime.now());
        em.persist(order);

        transaction.commit();
    }

    @Test
    void 복합키_만들기_IdClass(){
        transaction.begin();

        var parent = new Parentv1();
        parent.setId1("id1");
        parent.setId2("id2");
        em.persist(parent);
        transaction.commit();

        em.clear();
        var findParent = em.find(Parentv1.class, new ParentIdv1("id1", "id2"));
        log.info("{} {} ", findParent.getId1(), findParent.getId2());
    }

    @Test
    void 복합키_만들기_EmbeddedId_추천(){
        transaction.begin();

        var parent = new Parentv2();
        parent.setId(new ParentIdv2("id1", "id2"));
        em.persist(parent);
        transaction.commit();

        em.clear();
        var findParent = em.find(Parentv2.class, new ParentIdv2("id1", "id2"));
        log.info("{} {} ", findParent.getId().getId1(), findParent.getId().getId2());
    }
}
