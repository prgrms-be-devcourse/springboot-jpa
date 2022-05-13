package com.kdt.jpa.domain;

import com.kdt.jpa.domain.item.Food;
import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.OrderRepository;
import com.kdt.jpa.domain.order.OrderStatus;
import com.kdt.jpa.domain.parent.EmbeddedParent;
import com.kdt.jpa.domain.parent.EmbeddedParentId;
import com.kdt.jpa.domain.parent.IdClassParent;
import com.kdt.jpa.domain.parent.IdClassParentId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Slf4j
@SpringBootTest
@DisplayName("고급매핑 테스트")
public class ImproveMappingTest {
    
    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private OrderRepository orderRepository;

    private EntityManager em;
    private EntityTransaction transaction;


    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @AfterEach
    void reset() {
        em.clear();
        orderRepository.deleteAll();
    }


    @Test
    @DisplayName("상속 저장 테스트")
    void testInheritance() {
        // Given
        Food food = new Food();
        food.setPrice(1000);
        food.setStockQuantity(100);
        food.setChef("범석");

        // When
        transaction.begin();
        em.persist(food);
        transaction.commit();

        // Then
        assertThat(em.contains(food), is(true));
    }

    @Test
    @DisplayName("공통속성 테스트 - BaseEntity")
    void mapped_super_class_test() {
        // Given
        String orderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("---");
        order.setOrderDatetime(LocalDateTime.now());

        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy("beomsic");
        // When
        transaction.begin();
        em.persist(order);
        transaction.commit();

        // Then
        Order order1 = em.find(Order.class, orderId);
        assertThat(order1.getCreatedBy().equals("beomsic"), is(true));
    }

    @Test
    @DisplayName("복합키 테스트 - IdClass")
    void testIdClass() {
        // Given
        IdClassParent parent = new IdClassParent();
        parent.setId1("parentId1");
        parent.setId2("parentId2");
        // When
        transaction.begin();
        em.persist(parent);
        transaction.commit();
        // Then
        IdClassParent parent1 = em.find(IdClassParent.class, new IdClassParentId("parentId1", "parentId2"));
        assertThat(parent1, samePropertyValuesAs(parent));
    }

    @Test
    @DisplayName("복합키 테스트 - Embedded")
    void testEmbedded() {
        // Given
        EmbeddedParentId embeddedParentId = new EmbeddedParentId("parentId1", "parentId2");
        EmbeddedParent parent = new EmbeddedParent();
        parent.setId(embeddedParentId);

        // When
        transaction.begin();
        em.persist(parent);
        transaction.commit();

        // Then
        EmbeddedParent parent1 = em.find(EmbeddedParent.class, embeddedParentId);
        assertThat(parent1, samePropertyValuesAs(parent));

    }
}
