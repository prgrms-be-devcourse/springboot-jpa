package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class OrderPersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("(단방향, 양방향)주문과 멤버의 연관관계 테스트를 할 수 있다.")
    void RELATIONAL_ORDER_MEMBER_TEST(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = new Member("joje", "continue", 22, "paju", "smart");
        entityManager.persist(member);

        Order order = new Order("hihi", OrderStatus.ACCEPTED, member);
//        order.changeMember(member); // 이렇게 변경하는 메소드로도 멤버 필드를 완성할 수 있음
        entityManager.persist(order);
        transaction.commit();

        log.info("{} {}", member.getName(), member.getDescription());
        log.info("{} {}", order.getMemo(), order.getOrderStatus());


        entityManager.clear();

        Order entity = entityManager.find(Order.class, order.getId());

        log.info("{}", entity.getMember().getAge());
        log.info("{}", entity.getMember().getNickName());
    }
}