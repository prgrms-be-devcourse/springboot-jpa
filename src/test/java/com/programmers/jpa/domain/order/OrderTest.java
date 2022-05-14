package com.programmers.jpa.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    @DisplayName("주문을 생성한다.")
    void orderTest() {
        // Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //Member 생성
        Member member = Member.of("sampleName", "spring", 25, "판교");
        em.persist(member);

        //Item 생성
        Item item1 = Item.of("chicken", 18000, 100);
        Item item2 = Item.of("icecream", 10000, 50);
        em.persist(item1);
        em.persist(item2);

        //OrderItem 생성
        OrderItem orderItem1 = OrderItem.createOrderItem(item1, item1.getPrice() * 2, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(item2, item2.getPrice(), 1);

        // When
        String orderMemo = "문 앞에 두고 가주세요.";
        Order order = Order.createOrder(member, orderMemo, orderItem1, orderItem2);
        em.persist(order);

        tx.commit();

        em.clear(); //영속성 컨텍스트 초기화

        // Then
        Member findMember = em.find(Member.class, member.getId());
        assertThat(findMember).isNotNull();

        Item findItem1 = em.find(Item.class, item1.getId());
        Item findItem2 = em.find(Item.class, item2.getId());
        assertThat(findItem1).isNotNull();
        assertThat(findItem2).isNotNull();
        assertThat(findItem1.getStockQuantity()).isEqualTo(98);
        assertThat(findItem2.getStockQuantity()).isEqualTo(49);

        OrderItem findOrderItem1 = em.find(OrderItem.class, orderItem1.getId());
        OrderItem findOrderItem2 = em.find(OrderItem.class, orderItem2.getId());
        assertThat(findOrderItem1).isNotNull();
        assertThat(findOrderItem2).isNotNull();
        assertThat(findOrderItem1.getItem().getId()).isEqualTo(item1.getId());
        assertThat(findOrderItem2.getItem().getId()).isEqualTo(item2.getId());

        Order findOrder = em.find(Order.class, order.getUuid());
        assertThat(findOrder).isNotNull();
        assertThat(findOrder.getOrderItems().get(0).getId()).isEqualTo(orderItem1.getId());
        assertThat(findOrder.getOrderItems().get(1).getId()).isEqualTo(orderItem2.getId());
        assertThat(findOrder.getMember().getId()).isEqualTo(member.getId());
        assertThat(findOrder.getMemo()).isEqualTo(orderMemo);
    }
}