package com.part4.jpa2;

import com.part4.jpa2.domain.Member;
import com.part4.jpa2.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.part4.jpa2.domain.OrderStatus.OPENED;

@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert(){
        var memberBuilder = Member.builder();
        memberBuilder.name("subin");
        memberBuilder.address("게더타운");
        memberBuilder.age(24);
        memberBuilder.nickName("kimyo");
        memberBuilder.description("JPA 공부중");
        var member = memberBuilder.build();

        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(member);
        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        var memberBuilder = Member.builder();
        memberBuilder.name("subin");
        memberBuilder.address("게더타운");
        memberBuilder.age(24);
        memberBuilder.nickName("kimyo");
        memberBuilder.description("JPA 공부중");
        var member = memberBuilder.build();

        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L); // 영속화된 회원

        var orderBuilder = Order.builder();
        orderBuilder.uuid(UUID.randomUUID().toString());
        orderBuilder.orderDatetime(LocalDateTime.now());
        orderBuilder.orderStatus(OPENED);
        orderBuilder.memo("부재시 전화주세요.");
        orderBuilder.memberId(memberEntity.getId()); // 외래키를 직접 지정
        var order = orderBuilder.build();

        entityManager.persist(order);
        transaction.commit();


        Order orderEntity = entityManager.find(Order.class, order.getUuid());   // select order
        // FK 를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId()); // select member
        // orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?

    }

}
