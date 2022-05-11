package com.part4.jpa2;

import com.part4.jpa2.domain.order.Member;
import com.part4.jpa2.domain.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static com.part4.jpa2.Helper.makeMember;
import static com.part4.jpa2.Helper.makeOrder;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DisplayName("3-4 연관관계매핑")
public class 연관관계매핑 {
    @Autowired
    EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @Nested
    class 양방향연관관계 {
        @Test
        void 저장__편의메소드_사용() {
            transaction.begin();

            var member = makeMember();
            em.persist(member);
            var order = makeOrder();
            order.setMember(member);
            em.persist(order);


            var entity = em.find(Order.class, order.getUuid());
            assertThat(entity).usingRecursiveComparison()
                    .isEqualTo(order);

            transaction.commit();
        }

        @Test
        void 조회__객체그래프탐색_사용() {
            transaction.begin();

            var order = makeOrder();
            em.persist(order);

            var member = makeMember();
            member.addOrder(order);
            em.persist(member);
            transaction.commit();
            em.clear();

            // 조회 : 회원 -> 회원의 주문
            var findMember = em.find(Member.class, 1L);
            log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

            // 조회 : 주문 -> 주문한 회원
            var findOrder = em.find(Order.class, findMember.getOrders().get(0).getUuid());
            log.info("member-nickName: {}", findOrder.getMember().getNickName());

        }
    }


}
