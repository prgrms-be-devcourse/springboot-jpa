package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.springjpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
public class MemberPersistenceTest extends EntityManagerTest {

    @Test
    @DisplayName("연관관계 메소드를 통해 주문을 추가 할 수 있다.")
    void testAddOrder() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());

        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            member.addOrder(order);
        });
        Member findMember = entityManager.find(Member.class, member.getId());
        assertThat(findMember.getOrders().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Member2의 주문 Order2를 편의 메소드를 통해 Member1 한테 추가하면 Order2의 Member도 함께 변경되어야 한다.")
    void testAddOrderMemberUpdate() {
        Member member = new Member("태산2", UUID.randomUUID().toString(), 20, "주소2", "Desc2", new ArrayList<>());
        Member member2 = new Member("태산3", UUID.randomUUID().toString(), 30, "주소2", "Desc2", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        Order order2 = new Order("메모2", OPENED, LocalDateTime.now(), member, new ArrayList<>());

        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            member.addOrder(order);

            entityManager.persist(member2);
            entityManager.persist(order2);
            member2.addOrder(order2);
            member.addOrder(order2);
        });

        Member findMember = entityManager.find(Member.class, member.getId());
        Member findMember2 = entityManager.find(Member.class, member2.getId());
        assertAll(
                () -> assertThat(findMember.getOrders().size()).isEqualTo(2),
                () -> assertThat(findMember2.getOrders().size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("객체그래프탐색을_이용한_조회")
    void testGraphSearch() {
        Member member = new Member("태산4", UUID.randomUUID().toString(), 40, "주소2", "Desc2", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());

        execWithTransaction(() -> {
            entityManager.persist(order);
            entityManager.persist(member);
            member.addOrder(order);
        });

        Member findMember = entityManager.find(Member.class, member.getId());
        assertThat(findMember.getOrders().get(0).getUuid()).isEqualTo(order.getUuid());
    }
}