package org.programmers.springbootjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.springbootjpa.domain.Member;
import org.programmers.springbootjpa.domain.Order;
import org.programmers.springbootjpa.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.programmers.springbootjpa.domain.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    private static final String ORDER_UUID =  UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setName("KangHonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");

        em.persist(member);

        Order order = new Order();
        order.setUuid(ORDER_UUID);
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 현관에 버리고 가 주세요");
        order.setMember(member);

        em.persist(order);
        tx.commit();
    }

    @Test
    void proxy() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Order order = em.find(Order.class, ORDER_UUID);
        Member member = order.getMember();

        log.info("MEMBER USER BEFORE IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member)); //member -> proxy 객체

        member.getNickName();

        log.info("MEMBER USER AFTER IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member)); //proxy 초기화
    }

    @Test
    void move_persist() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Order order = em.find(Order.class, ORDER_UUID);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(10);
        orderItem.setPrice(1000);

//        order.addOrderItem(orderItem);  //영속성 전이 발생
        orderItem.setOrder(order);

        tx.commit();

        em.clear();

        tx.begin();

        Order foundOrder = em.find(Order.class, ORDER_UUID);

        foundOrder.getOrderItems().remove(0);   //고아상태 -> flush() 발생 시 DB 에서도 삭제 반영

        tx.commit();
    }
}
