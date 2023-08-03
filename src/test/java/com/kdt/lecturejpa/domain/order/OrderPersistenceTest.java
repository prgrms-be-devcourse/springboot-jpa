package com.kdt.lecturejpa.domain.order;

import static com.kdt.lecturejpa.domain.order.OrderStatus.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
		Member member = new Member();
		member.setName("byeonggon");
		member.setAddress("서울시 관악구 신림동");
		member.setAge(25);
		member.setNickName("gorani");
		member.setDescription("백엔드 개발자에요");

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);

		transaction.commit();
	}

	@Test
	void 잘못된_설계() {
		Member member = new Member();
		member.setName("kanghonggu");
		member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
		member.setAge(33);
		member.setNickName("guppy.kang");
		member.setDescription("백앤드 개발자에요.");

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(member);
		Member memberEntity = entityManager.find(Member.class, 1L);

		Order order = new Order();
		order.setOrderDateTime(LocalDateTime.now());
		order.setOrderStatus(OPENED);
		order.setMemo("부재시 전화주세요.");
		order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정

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

		Member member = new Member();
		member.setName("byeonggon");
		member.setNickName("guppy.kang");
		member.setAddress("서울시 관악구");
		member.setAge(33);

		entityManager.persist(member);

		Order order = new Order();
		order.setOrderStatus(OPENED);
		order.setOrderDateTime(LocalDateTime.now());
		order.setMemo("부재시 연락주세요.");
		order.setMember(member);
		//member.setOrders(Lists.newArrayList(order)); //이 코드가 없어도 setMember를 수정해서 양방향으로 업데이트 되

		entityManager.persist(order);
		transaction.commit();
		Order entity = entityManager.find(Order.class, order.getId());

		log.info("{}", entity.getMember().getNickName()); // 객체 탐색 그래프
		log.info("{}", entity.getMember().getOrders().size());
		log.info("{}", order.getMember().getOrders().size());
	}
}
