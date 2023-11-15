package com.sehee.weeklyjpa;

import com.sehee.weeklyjpa.domain.order.Member;
import com.sehee.weeklyjpa.domain.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.sehee.weeklyjpa.domain.order.OrderStatus.OPENED;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class ProxyTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    Order order;
    Member member;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        persistNewOrderWithoutMemberAndOrderItem();
        persistNewMemberWithoutOrder();
        order.setMember(member);
        transaction.commit();
    }

    void persistNewOrderWithoutMemberAndOrderItem() {
        order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("This is memo.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);
        log.info("persist order!");
    }

    void persistNewMemberWithoutOrder() {
        member = new Member();
        member.setName("sehee");
        member.setNickName("LeeSehee");
        member.setAge(25);
        member.setAddress("Seoul");
        member.setDescription("This is description.");

        entityManager.persist(member);
        log.info("persist member!");
    }

    @Test
    void proxy() {
        entityManager.clear();
        log.info("clear");

        Member retrievedMember = entityManager.find(Member.class, member.getId());
        log.info("load member");

        List<Order> retrievedOrders = retrievedMember.getOrders(); // proxy
        log.info("load order? {}", entityManagerFactory.getPersistenceUnitUtil().isLoaded(retrievedOrders));
        Order retrievedOrder = retrievedOrders.get(0); // LAZY: 여기서 쿼리 출력
        log.info("load order? {}", entityManagerFactory.getPersistenceUnitUtil().isLoaded(retrievedOrder));

    }

    @Test
    void proxy2() {
        assertThrows(LazyInitializationException.class, () -> {
                    entityManager.clear(); // 1차 캐시 지우기
                    Member retrievedMember = entityManager.find(Member.class, member.getId()); // 엔티티
                    List<Order> proxyOrders = retrievedMember.getOrders(); // 프록시
                    log.info("load order? {}", entityManagerFactory.getPersistenceUnitUtil().isLoaded(proxyOrders));
                    entityManager.clear(); // 영속성 컨텍스트에서 엔티티 및 프록시 클리어 -> 프록시는 준영속
                    log.info("detach retrievedMember");
                    transaction.begin();
                    transaction.commit(); // nothing happened
                    proxyOrders.get(0); // 초기화 -> LazyInitializationException
                }
        );
    }
}
