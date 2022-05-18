package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.jpasettingpractice.domain.Orderstatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class RelationMappingTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("연관관계 매핑확인")
    void RELATION_TEST() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        OrderItem orderItem = new OrderItem();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("call me maybe");
        order.addOrderItem(orderItem);

        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        member.setAge(29);
        member.setAddress("daegu");
        member.setDescription("hi");
        member.addOrder(order);
        entityManager.persist(member);

        Item item = new Item();
        item.setPrice(1000);
        item.setStockQuantity(2);

        entityManager.persist(item);

        item.addOrderItem(orderItem);

        transaction.commit();

        Member orderMember = order.getMember();
        assertThat(orderMember).isEqualTo(member);

        Order memberOrder = member.getOrders().get(0);
        Order orderItemOrder = orderItem.getOrder();
        assertThat(memberOrder).isEqualTo(order);
        assertThat(orderItemOrder).isEqualTo(order);

        OrderItem orderOrderItem = order.getOrderItems().get(0);
        OrderItem itemOrderItem = item.getOrderItems().get(0);
        assertThat(orderOrderItem).isEqualTo(orderItem);
        assertThat(itemOrderItem).isEqualTo(orderItem);
    }
}
