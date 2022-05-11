package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.order.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.persist(member);

        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.persist(member);
        Member memberEntity = em.find(Member.class, 1L);

        Order order = new Order(
                UUID.randomUUID().toString()
                , "부재 시 전화줘요"
                , OrderStatus.OPENED
                , LocalDateTime.now()
                , memberEntity
        );
        em.persist(order);
        transaction.commit();

        Order orderEntity = em.find(Order.class, order.getUuid()); // select Order
        //데이터 중심 설계에서는 order의 member를 구해오기 위해 다시 회원 조회가 필수
        Member orderMemberEntity = em.find(Member.class, orderEntity.getMember()); //select member
        //orderEntity.getMember // 객체 중심 설계라면 이렇게 객체 그램프 탐색을 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }

    @Test
    void 연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        em.persist(member);

        Order order = new Order(
                UUID.randomUUID().toString()
                , "부재 시 전화줘요"
                , OrderStatus.OPENED
                , LocalDateTime.now()
                , member
        );

        em.persist(order);

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getName());
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());
    }
}
