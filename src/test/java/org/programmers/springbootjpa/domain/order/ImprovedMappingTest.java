package org.programmers.springbootjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.programmers.springbootjpa.domain.*;
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
public class ImprovedMappingTest {
    
    @Autowired
    private EntityManagerFactory emf;
    
    @Test
    void 상속_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();

        Food food = new Food();
        food.setPrice(1000);
        food.setStockQuantity(100);
        food.setChef("백종원");

        em.persist(food);

        tx.commit();
    }

    @Test
    void mappedSuperClass_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Member member = createTestMember();
        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 현관에 버리고 가 주세요");
        order.setMember(member);

        order.setCreatedBy("OrderMaker");
        order.setCreatedAt(LocalDateTime.now());

        em.persist(order);

        tx.commit();
    }

    private Member createTestMember() {
        Member member = new Member();
        member.setName("KangHonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");
        return member;
    }

    @Test
    void id_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Parent parent = new Parent();
        parent.setId1("id1");
        parent.setId2("id2");

        em.persist(parent);

        tx.commit();

        em.clear();

        Parent foundParent = em.find(Parent.class, new ParentId("id1", "id2"));
        log.info("{} {}", foundParent.getId1(), foundParent.getId2());
    }
}
