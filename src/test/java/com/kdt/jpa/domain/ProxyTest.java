package com.kdt.jpa.domain;

import com.kdt.jpa.domain.member.Member;
import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.OrderItem;
import com.kdt.jpa.domain.order.OrderRepository;
import com.kdt.jpa.domain.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    OrderRepository orderRepository;

    private String uuid = UUID.randomUUID().toString();
    private EntityManager em;
    private EntityTransaction transaction;
    private Order order;
    private Member member;

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();

        transaction.begin();
        order = new Order();
        order.setOrderId(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        em.persist(order);

        member = new Member();
        member.setName("beomseok");
        member.setAddress("서울시 구로구(만) 그렇구만");
        member.setAge(26);
        member.setNickname("beomsic");
        member.setDescription("화이팅 !!!");

        member.addOrder(order);
        em.persist(member);

        transaction.commit();
    }

    @AfterEach
    void reset() {
        em.close();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Proxy - 지연 로딩 테스트")
    void testProxy() {
        // Given
        EntityManager entityManager = emf.createEntityManager();
        // When
        Order order1 = entityManager.find(Order.class, uuid);
        Member member1 = order1.getMember();
        // Then
        assertThat(emf.getPersistenceUnitUtil().isLoaded(member1), is(false));
        member1.getAge();
        assertThat(emf.getPersistenceUnitUtil().isLoaded(member1), is(true));
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    void testCascade() {
        // Given
        Order order1 = em.find(Order.class, uuid);
        OrderItem item = new OrderItem();
        item.setPrice(2000);
        item.setQuantity(20);

        // When
        transaction.begin();
        order1.addOrderItem(item);
        transaction.commit();
        // item은 영속성 상태 변경을 하지 않았지만,
        // 영속성 전이를 통해 order 엔티티를 영속성 상태 변경했기 때문에 함께 저장

        // Then
        assertThat(em.contains(item) , is(true));
    }

    @Test
    @DisplayName("고아 객체 테스트")
    void testOrphan() {
        // Given
        Member member1 = em.find(Member.class, member.getId());

        // When
        member1.getOrders().remove(0); // order 삭제 -> 고아객체
        transaction.begin();
        transaction.commit();

        // Then
        assertThat(em.contains(member1), is(false));

    }
}
