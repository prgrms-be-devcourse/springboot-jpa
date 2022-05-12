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

import javax.persistence.EntityManagerFactory;
import java.util.UUID;

import static com.part4.jpa2.Helper.*;

@Slf4j
@DataJpaTest
@DisplayName("3-6 프록시 객체")
public class 프록시객체 {
    @Autowired
    private EntityManagerFactory emf;
    private String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void MEMBER_1_ORDER_1_생성(){
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();
        transaction.begin();

        var order = makeOrderWithUUID(uuid);
        em.persist(order);

        Member member = makeMember();
        member.addOrder(order);
        em.persist(member);

        transaction.commit();
    }

    @Nested
    class 지연로딩_즉시로딩{
        @Test
        void LAZY_AND_EAGER_FETCH(){
            var em = emf.createEntityManager();
            var order = em.find(Order.class,uuid);

            var member = order.getMember();
            log.info("MEMBER USE BEFORE IS_LOADED: {}",emf.getPersistenceUnitUtil().isLoaded(member));

            var nickName = member.getNickName();
            log.info("MEMBER USE AFTER IS_LOADED: {}",emf.getPersistenceUnitUtil().isLoaded(member));
        }
    }

    @Nested
    class 영속성전이{
        @Test
        void CascadeType(){
            var em = emf.createEntityManager();
            var transaction = em.getTransaction();

            var order = em.find(Order.class, uuid);

            transaction.begin();
            var item = makeOrderItem();
            order.addOrderItem(item);
            transaction.commit();
        }
    }

    @Nested
    class 고아객체{
        @Test
        void orphanRemoval(){
            var em = emf.createEntityManager();
            var transaction = em.getTransaction();

            var order = em.find(Order.class, uuid);

            transaction.begin();

            var item = makeOrderItem();
            order.addOrderItem(item);
            transaction.commit();

            em.clear();

            var order2 = em.find(Order.class, uuid);
            transaction.begin();
            order2.getOrderItems().remove(0);

            transaction.commit();
        }
    }

}
