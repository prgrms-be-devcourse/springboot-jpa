package com.example.kdtjpa.domain.order;

import static com.example.kdtjpa.domain.order.OrderStatus.OPENED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional // test코드에는 Transacitonal 필요
class OrderPersistenceTest {

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager entityManager;
	private EntityTransaction transaction;

	@BeforeEach
	void setup() {
		entityManager = emf.createEntityManager();
		transaction = entityManager.getTransaction();
	}

	@AfterEach
	void close() {
		entityManager.clear();
	}

	@Test
	void 양방향관계_저장() {
		transaction.begin();

		Member member = new Member("kanghonggu", "guppy.kang", 33, "서울시 동작구만 움직이면쏜다.", "KDT 화이팅");
		entityManager.persist(member);

		Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "부재시 전화주세요.", member);
		entityManager.persist(order);

		member.getOrders().add(order); // 연관관계의 주인이 아닌곳(member)에만 SETTING

		List<Order> orders = member.getOrders();

		for (Order o : orders) {
			log.info("{}", o.toString());
		}

		transaction.commit();
	}

	@Test
	void 양방향관계_편의메소드_테스트() {
		transaction.begin();

		Member member = new Member( "sueyon", "susu", 23, "deagu", "KDT 화이팅");
		entityManager.persist(member);

		Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "부재시 전화주세요.", member);
		entityManager.persist(order);

		Order order2 = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "부재시 2번째 여기로 전화주세요.", member);
		entityManager.persist(order2);

		member.addOrder(order);
		member.addOrder(order2);

		transaction.commit();

		// 저장 확인
		List<Order> orders = member.getOrders();

		for (Order o : orders) {
			log.info("{}", o.toString()); // 위에서 저장된 order 2개나옴
		}

	}

	@Test
	void 객체그래프탐색을_이용한_조회() {
		transaction.begin();

		Member member = new Member("sueyon", "jjangsu", 23, "deagu", "KDT 화이팅");
		entityManager.persist(member);

		Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "부재시 전화주세요.", member);
		entityManager.persist(order);

		member.addOrder(order);

		transaction.commit();
		entityManager.clear();

		// 회원 조회 -> 회원의 주문 까지 조회
		Member findMember = entityManager.find(Member.class, 1L);
		log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

		// 주문조회 -> 주문한 회원 조회
		Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
		log.info("member-nickName: {}", findOrder.getMember().getNickName());
	}

	@Test
	void mapped_super_class_test() {
		transaction.begin();

		Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "부재시 전화주세요.");

		order.setCreatedAt(LocalDateTime.now());
		order.setCreatedBy("suyeonjang");

		entityManager.persist(order);

		transaction.commit();
	}

}
