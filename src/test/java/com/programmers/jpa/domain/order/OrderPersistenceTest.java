package com.programmers.jpa.domain.order;

import com.programmers.jpa.domain.Member;
import com.programmers.jpa.domain.Order;
import com.programmers.jpa.domain.OrderStatus;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void Member_Order_연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member("hanju", "yanju", 27, "서울", "수강생");

        em.persist(member);

        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OrderStatus.OPENED, "부재시 연락", member);

        em.persist(order);
        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
    }

}
