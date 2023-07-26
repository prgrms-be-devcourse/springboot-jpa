package com.prgms.springbootjpa.mission3;

import com.prgms.springbootjpa.domain.Item;
import com.prgms.springbootjpa.domain.Order;
import com.prgms.springbootjpa.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RelationMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    @DisplayName("관계 매핑 테스트")
    void 주문() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        Item item = new Item(10L, 10L, "test");
        OrderItem orderItem = new OrderItem(item);
        Order order = new Order(List.of(orderItem), ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        em.persist(order);
        transaction.commit();

        //then
        Order findOrder = em.find(Order.class, order.getId());
        OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());
        Item findItem = em.find(Item.class, item.getId());
        assertThat(findOrder).isEqualTo(order);
        assertThat(findOrderItem).isEqualTo(orderItem);
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    @DisplayName("지연로딩 시 프록시 객체가 들어있다.")
    void 프록시() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        Item item = new Item(10L, 10L, "test");
        OrderItem orderItem = new OrderItem(item);
        Order order = new Order(List.of(orderItem), ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        em.persist(order);
        transaction.commit();
        em.clear();
        Order findOrder = em.find(Order.class, order.getId());

        //then
        PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
        assertThat(persistenceUnitUtil.isLoaded(findOrder, "orderItems")).isFalse();
    }
}
