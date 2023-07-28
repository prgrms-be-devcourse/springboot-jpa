package com.devcourse.springbootjpa;

import static com.devcourse.springbootjpa.domain.order.OrderStatus.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devcourse.springbootjpa.domain.order.Member;
import com.devcourse.springbootjpa.domain.order.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
class OrderPersistenceTest {

	@Autowired
	EntityManagerFactory emf;

	@Test
	void member_insert() {
		Member member = Member.builder()
				.name("seungwon")
				.address("서울시 동작구")
				.nickname("sw")
				.description("백둥이")
				.age(26)
				.build();

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);

		transaction.commit();
	}

	@Test
	void 연관관계_테스트() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Member member = Member.builder()
				.name("seungwon")
				.address("서울시 동작구")
				.nickname("baekdung")
				.description("백둥이")
				.age(26)
				.build();

		entityManager.persist(member);

		Order order = Order.builder()
				.uuid(UUID.randomUUID().toString())
				.orderStatus(OPENED)
				.orderDateTime(LocalDateTime.now())
				.memo("부재시 연락주세요")
				.member(member)
				.build();

		entityManager.persist(order);

		transaction.commit();

		entityManager.clear();
		Order entity = entityManager.find(Order.class, order.getUuid());

		log.info("{}", entity.getMember().getNickname());
		log.info("{}", entity.getMember().getOrders().size());
	}
}
