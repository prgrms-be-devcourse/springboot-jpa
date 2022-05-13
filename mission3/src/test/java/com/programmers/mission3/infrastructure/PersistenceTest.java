package com.programmers.mission3.infrastructure;

import com.programmers.mission3.Infrastructure.domain.item.Food;
import com.programmers.mission3.Infrastructure.domain.item.Furniture;
import com.programmers.mission3.Infrastructure.domain.item.Item;
import com.programmers.mission3.Infrastructure.domain.member.Member;
import com.programmers.mission3.Infrastructure.domain.order.Order;
import com.programmers.mission3.Infrastructure.domain.order.OrderItem;
import org.assertj.core.internal.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.List;
import java.util.UUID;

import static com.programmers.mission3.Infrastructure.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("Member Order 양방향 관계 검증")
    void testMemberAndOrder(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // given
        transaction.begin();

        Member member = Member.builder()
                .name("jung")
                .nickName("kyongily")
                .age(145)
                .address("경기도 구리시")
                .description("그만 먹어")
                .build();

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("음식은 식지않게")
                .orderStatus(OPENED)
                .build();

        order.setMember(member);

        em.persist(member);

        transaction.commit();

        // when
        transaction.begin();

        Member maybeMember = em.find(Member.class, 1L);

        // then
        assertThat(maybeMember).isNotNull();
        assertThat(member.getOrders().size()).isEqualTo(1);
        assertThat(order.getMember().getId()).isEqualTo(1);

        transaction.commit();
    }

    @Test
    @DisplayName("Order와 OrderItem 양방향 관계 검증")
    void testOrderAndOrderItem(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //given

        transaction.begin();

        String orderId = UUID.randomUUID().toString();

        Order order = Order.builder()
                .uuid(orderId)
                .memo("음식은 식지않게, 가구는 튼튼하게")
                .orderStatus(OPENED)
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .price(3000)
                .quantity(3)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .price(50000)
                .quantity(2)
                .build();

        //when
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);
        orderItems.forEach(order::addOrderItem);

        em.persist(order);
        orderItems.forEach(em::persist);

        transaction.commit();

        //then
        transaction.begin();

        Order retrievedOrder = em.find(Order.class,orderId);
        assertThat(retrievedOrder.getOrderItems().size()).isEqualTo(2);
        assertThat(em.find(OrderItem.class,1L).getOrder().getUuid()).isEqualTo(orderId);
        assertThat(em.find(OrderItem.class,2L).getOrder().getUuid()).isEqualTo(orderId);

        transaction.commit();
    }
}
