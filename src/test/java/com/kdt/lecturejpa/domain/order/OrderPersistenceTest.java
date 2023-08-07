package com.kdt.lecturejpa.domain.order;

import static com.kdt.lecturejpa.domain.order.OrderStatus.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdt.lecturejpa.domain.member.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

	@Autowired
	EntityManagerFactory emf;

	@Test
	void member_insert() {
		Member member = Member.builder()
			.name("byeonggon")
			.address("서울시 관악구 신림동")
			.age(25)
			.nickName("gorani")
			.description("백엔드 개발자에요")
			.build();

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);

		transaction.commit();
	}

	@Test
	void 잘못된_설계() {
		Member member = Member.builder()
			.name("kanghonggu")
			.address("서울시 동작구(만) 움직이면 쏜다.")
			.age(33)
			.nickName("guppy.kang")
			.description("백앤드 개발자에요.")
			.build();

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);
		Member memberEntity = entityManager.find(Member.class, 1L);

		Order order = Order.builder()
			.orderDateTime(LocalDateTime.now())
			.orderStatus(OPENED)
			.memo("부재시 전화주세요.")
			.memberId(memberEntity.getId()) // 외래키를 직접 지정
			.build();

		entityManager.persist(order);
		transaction.commit();

		Order orderEntity = entityManager.find(Order.class, order.getId());
		// FK 를 이용해 회원 다시 조회
		Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
		// orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
		log.info("nick : {}", orderMemberEntity.getNickName());
	}

	@Test
	void 연관관계_테스트() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Member member = Member.builder()
			.name("byeonggon")
			.nickName("guppy.kang")
			.address("서울시 관악구")
			.age(33)
			.build();

		entityManager.persist(member);

		Order order = Order.builder()
			.orderStatus(OPENED)
			.orderDateTime(LocalDateTime.now())
			.memo("부재시 연락주세요.")
			.build();
		order.attachMember(member);

		entityManager.persist(order);
		transaction.commit();
		Order entity = entityManager.find(Order.class, order.getId());

		log.info("{}", entity.getMember().getNickName()); // 객체 탐색 그래프
		log.info("{}", entity.getMember().getOrders().size());
		log.info("{}", order.getMember().getOrders().size());
	}
}
