package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.springjpa.domain.order.EntityUtil.getNewMember;
import static com.example.springjpa.domain.order.Member.MemberBuilder;
import static com.example.springjpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberPersistenceTest extends EntityManagerTest {

    @Test
    @DisplayName("연관관계 메소드를 통해 주문을 추가 할 수 있다.")
    void testAddOrder() {
        Member member = getNewMember();
        Order order = new Order(UUID.randomUUID().toString(), "메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
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
        Member member = getNewMember();
        Member member2 = getNewMember();

        Order order = new Order(UUID.randomUUID().toString(), "메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        Order order2 = new Order(UUID.randomUUID().toString(), "메모2", OPENED, LocalDateTime.now(), member, new ArrayList<>());

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
                () -> assertThat(findMember2.getOrders().size()).isZero()
        );
    }

    @Test
    @DisplayName("객체그래프탐색을_이용한_조회")
    void testGraphSearch() {
        Member member = getNewMember();
        Order order = new Order(UUID.randomUUID().toString(), "메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());

        execWithTransaction(() -> {
            entityManager.persist(order);
            entityManager.persist(member);
            member.addOrder(order);
        });

        Member findMember = entityManager.find(Member.class, member.getId());
        assertThat(findMember.getOrders().get(0).getUuid()).isEqualTo(order.getUuid());
    }

    @Test
    @DisplayName("age는 음수값이 들어올 수 없다.")
    void testAgeValidate() {
        assertThrows(IllegalArgumentException.class, () -> new MemberBuilder()
                .name("태산")
                .nickName(UUID.randomUUID().toString())
                .age(-1)
                .address("주소")
                .description("Desc")
                .orders(new Orders())
                .build());
    }

    @Test
    @DisplayName("이름과 닉네임은 null과 50자 초과를 허용하지 않는다.")
    void testNameValidate() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Member.builder()
                        .name("*".repeat(51))
                        .nickName(UUID.randomUUID().toString())
                        .age(10)
                        .address("주소")
                        .description("Desc")
                        .orders(new Orders())
                        .build()),
                () -> assertThrows(IllegalArgumentException.class, () -> Member.builder()
                        .name("name")
                        .nickName("*".repeat(51))
                        .age(10)
                        .address("주소")
                        .description("Desc")
                        .orders(new Orders())
                        .build()),
                () -> assertThrows(IllegalArgumentException.class, () -> Member.builder()
                        .name(null)
                        .nickName(UUID.randomUUID().toString())
                        .age(10)
                        .address("주소")
                        .description("Desc")
                        .orders(new Orders())
                        .build()),
                () -> assertThrows(IllegalArgumentException.class, () -> Member.builder()
                        .name("name")
                        .nickName(null)
                        .age(10)
                        .address("주소")
                        .description("Desc")
                        .orders(new Orders())
                        .build())
        );
    }
}