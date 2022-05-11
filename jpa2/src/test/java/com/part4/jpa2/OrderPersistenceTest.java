package com.part4.jpa2;

import com.part4.jpa2.domain.Member;
import com.part4.jpa2.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        Member memberEntity = entityManager.find(Member.class, 1L);

        var orderBuilder = Order.builder();
        orderBuilder.uuid(UUID.randomUUID().toString());
        orderBuilder.orderDatetime(LocalDateTime.now());
        orderBuilder.orderStatus(OPENED);
        orderBuilder.memo("부재시 전화주세요.");
        orderBuilder.memberId(memberEntity.getId());
        var order = orderBuilder.build();

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
    }

}
