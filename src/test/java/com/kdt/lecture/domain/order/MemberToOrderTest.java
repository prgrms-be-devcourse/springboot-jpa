package com.kdt.lecture.domain.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberToOrderTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;
    EntityTransaction entityTransaction;
    Order order;
    Member member;

    @BeforeEach
    void entityPersist() {
        entityManager = emf.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        order = EntityRegister.order(entityManager);
        member = EntityRegister.member(entityManager);
        order.setMember(member);
        entityTransaction.commit();
    }

    @Test
    @DisplayName("조회")
    void memberToOrderFindTest() {
        assertThat(member.getOrders().get(0)).usingRecursiveComparison().isEqualTo(order);
        assertThat(order.getMember()).usingRecursiveComparison().isEqualTo(member);
    }

    @Test
    @DisplayName("업데이트")
    void updateTest() {
        entityTransaction.begin();
        Member memberEntity = entityManager.find(Member.class, member.getId());
        memberEntity.setName("yongcheolkim");
        entityTransaction.commit();

        entityManager.clear();
        Order orderEntity = entityManager.find(Order.class, order.getId());
        assertThat(orderEntity.getMember().getName()).isEqualTo("yongcheolkim");
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        entityTransaction.begin();
        entityManager.remove(order);
        entityManager.remove(member);
        entityTransaction.commit();

        Member memberEntity = entityManager.find(Member.class, member.getId());
        Order orderEntity = entityManager.find(Order.class, order.getId());
        assertThat(memberEntity).isNull();
        assertThat(orderEntity).isNull();
    }
}