package com.example.lecturejpa.domain.order;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

	@Autowired
	EntityManagerFactory emf;

	@Test
	void 멤버_삽입_테스트() {
		Member member = new Member("jinhyungPark", "pjh", 27, "부산시 금정구", "주니어 개발자 지망생");

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(member);

		assertThat(entityManager.contains(member)).isTrue();
	}

	@Test
	void 잘못된_설계() {
		Member member = new Member("jinhyungPark", "pjh", 27, "부산시 금정구", "주니어 개발자 지망생");

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);
		Member memberEntity = entityManager.find(Member.class, 1L);

		Order order = new Order("부재시 전화주세요.", OrderStatus.OPENED, member);

		entityManager.persist(order);
		transaction.commit();

		Order orderEntity = entityManager.find(Order.class, order.getUuid());
		// FK 를 이용해 회원 다시 조회
		Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
		// orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
		log.info("nick : {}", orderMemberEntity.getNickName());
	}

	@Test
	void 연관관계_테스트() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		Member member = new Member("jinhyungPark", "pjh", 27, "부산시 금정구", "주니어 개발자 지망생");
		em.persist(member);
		Order order = new Order("부재시 연락주세요.", OrderStatus.OPENED, member);
		em.persist(order);
		transaction.commit();

		em.clear();
		Order foundOrder = em.find(Order.class, order.getUuid());

		log.info("{}", foundOrder.getMember().getNickName());// 객체 그래프 탐색
		log.info("{}", order.getMember().getOrders().size());
		assertThat(order.getMember().getOrders().size()).isEqualTo(1);
	}

	@Test
	void 주문_추가_테스트() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		Member member = new Member("jinhyungPark", "pjh", 27, "부산시 금정구", "주니어 개발자 지망생");
		em.persist(member);
		Item itemA = new Item(10,1);
		em.persist(itemA);
		Order order = new Order("부재시 연락주세요.", OrderStatus.OPENED, member);
		em.persist(order);
		OrderItem orderItem = new OrderItem(itemA.getPrice(), 1, order, itemA);
		em.persist(orderItem);
		transaction.commit();

		em.clear();
		Member foundMember = em.find(Member.class, member.getId());
		Item foundItem = em.find(Item.class, itemA.getId());
		Order foundOrder = em.find(Order.class, order.getUuid());
		OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

		assertThat(foundMember.getOrders().size()).isEqualTo(1);
		assertThat(itemA.getOrderItems().size()).isEqualTo(1);
		assertThat(order.getOrderItems().size()).isEqualTo(1);
	}
}
