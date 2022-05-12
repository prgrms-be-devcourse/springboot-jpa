package com.programmers.jpa.domain.order;

import com.programmers.jpa.domain.Member;
import com.programmers.jpa.domain.Order;
import com.programmers.jpa.domain.OrderItem;
import com.programmers.jpa.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void Member_Order_연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member("hanju", "yanju", 27, "서울", "수강생");

        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OrderStatus.OPENED, "부재시 연락", member, null);
        em.persist(order);

        member.setOrders(List.of(order));
        em.persist(member);

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        assertAll(
                () -> assertThat(entity.getMember().getNickName()).isEqualTo(member.getNickName()),
                () -> assertThat(entity.getMember().getOrders().size()).isEqualTo(member.getOrders().size())
        );
    }

    @Test
    void Order_OrderItem_연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OrderStatus.OPENED, "부재시 연락", null, null);

        OrderItem orderItem1 = new OrderItem(1000, 2, order, null);
        em.persist(orderItem1);

        OrderItem orderItem2 = new OrderItem(2000, 3, order, null);
        em.persist(orderItem2);

        order.setOrderItems(List.of(orderItem1, orderItem2));
        em.persist(order);

        transaction.commit();

        OrderItem entity1 = em.find(OrderItem.class, orderItem1.getId());
        OrderItem entity2 = em.find(OrderItem.class, orderItem2.getId());

        assertAll(
                () -> assertThat(entity1.getOrder().getMemo()).isEqualTo(order.getMemo()),
                () -> assertThat(entity2.getOrder().getMemo()).isEqualTo(order.getMemo()),
                () -> assertThat(entity1.getOrder().getUuid()).isEqualTo(entity2.getOrder().getUuid()),
                () -> assertThat(entity1.getOrder().getOrderItems().size()).isEqualTo(entity2.getOrder().getOrderItems().size())
        );
    }


}
