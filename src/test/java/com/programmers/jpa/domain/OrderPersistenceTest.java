package com.programmers.jpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@DataJpaTest
class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = Member.builder()
                .name("박이슬")
                .address("경기도 팡띵")
                .age(20)
                .nickName("슬")
                .description("백엔드 개발자 하고싶어요.")
                .build();

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
    }
    
    @Test
    void 잘못된_설계() {
        Member member = Member.builder()
                .name("박이슬")
                .address("경기도 팡띵")
                .age(20)
                .nickName("슬")
                .description("백엔드 개발자 하고싶어요.")
                .build();

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);
        
        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memo("부재 시 전화주세요.")
                .memberId(memberEntity.getId()) // 외래키를 직접 지정
                .build();
        
        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // orderEntity.getMember() // 객체중심 설계라면 이렇게 해야하지 않을까?
        log.info("nickname: {}", orderMemberEntity.getNickName());
    }
}