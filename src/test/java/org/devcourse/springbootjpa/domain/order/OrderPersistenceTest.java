package org.devcourse.springbootjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("order, order_item, item의 연관관계 매핑을 검증한다.")
    void testRelation() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Member member = new Member();
        member.setName("Cho");
        member.setAge(20);
        member.setAddress("경기");
        member.setNickName("ZZAMBA");
        member.setDescription("desc");

        em.persist(member);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(10);
        orderItem.setPrice(2000);

        Food item = new Food();
        item.setChef("chef");
        item.setPrice(1000);
        item.setStockQuantity(10);

        orderItem.addItem(item);

        Order order = new Order();
        order.setOrderStatus(OrderStatus.OPENED);
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요");
        order.setMemberId(member.getId());
        order.setLocalDateTime(LocalDateTime.now());
        order.setMember(member);
        order.addOrderItem(orderItem);

        em.persist(order);
        em.persist(orderItem);
        em.persist(item);

        tx.commit();

        em.clear();
        Order foundOrder = em.find(Order.class, order.getUuid());

        assertThat(foundOrder.getMember().getName()).isEqualTo("Cho");
        assertThat(foundOrder.getOrderItems()).hasSize(1);
        assertThat(foundOrder.getOrderItems().get(0).getPrice()).isEqualTo(2000);
        assertThat(foundOrder.getOrderItems().get(0).getItems()).hasSize(1);
        assertThat(foundOrder.getOrderItems().get(0).getItems().get(0).getStockQuantity()).isEqualTo(10);
    }
}
