package com.kdt.lecture.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    Member member = new Member("hyounguk", "agumon", 27, "seoul", "hello");
    Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OrderStatus.OPENED, "new Order");
    OrderItem orderItem = new OrderItem(5000, 10);
    Item item = new Food(5000, 100, "백종원");

    @Test
    @DisplayName("주문을 저장할 때 맴버도 같이 저장된다.")
    void 주문_저장_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // WHEN
        order.setMember(member);
        em.persist(order); // 맴버 데이터까지 한번에 insert가 실행됨
        transaction.commit();
        em.clear();

        // THEN
        Order orderEntity = em.find(Order.class, this.order.getId()); // select 쿼리가 날라감
        Member memberEntity = orderEntity.getMember(); // select 쿼리가 날라감
        assertThat(memberEntity.getName()).isEqualTo(member.getName());
        assertThat(orderEntity.getId()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("주문 아이템을 저장 후 주문을 가져와 지연로딩을 통해 주문 아이템을 가져온다")
    void 주문_지연로딩_테스트() {
        // GIVEN
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // WHEN
        orderItem.setOrder(order);
        orderItem.setItem(item);
        em.persist(orderItem); // 쿼리가 함께 날라감
        transaction.commit();
        em.clear();

        // THEN
        Order orderEntity = em.find(Order.class, this.order.getId());
        List<OrderItem> orderItems = orderEntity.getOrderItems();
        assertThat(orderItems).isNotEmpty();
    }


}
