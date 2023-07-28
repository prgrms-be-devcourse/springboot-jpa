package com.example.kdtjpa.domain.order;

import static com.example.kdtjpa.domain.order.OrderStatus.OPENED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
	EntityManagerFactory emf;
	private EntityManager entityManager;
	private EntityTransaction transaction;

	@BeforeEach
	void setup() {
		entityManager = emf.createEntityManager();
		transaction = entityManager.getTransaction();
	}

	@Test
	void 양방향관계_저장() {
		transaction.begin();

		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setMemo("부재시 전화주세요.");
		order.setOrderDatetime(LocalDateTime.now());
		order.setOrderStatus(OPENED);

		entityManager.persist(order);

		Member member = new Member();
		member.setName("kanghonggu");
		member.setNickName("guppy.kang");
		member.setAge(33);
		member.setAddress("서울시 동작구만 움직이면쏜다.");
		member.setDescription("KDT 화이팅");

		member.getOrders().add(order); // 연관관계의 주인이 아닌곳(member)에만 SETTING

		entityManager.persist(member);

		List<Order> orders = member.getOrders();
		for (Order o : orders) {
			log.info("{}", o.toString());
		}

		transaction.commit();
	}

	@Test
	void 양방향관계_편의메소드_테스트() {
		transaction.begin();

		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderDatetime(LocalDateTime.now());
		order.setOrderStatus(OPENED);
		order.setMemo("부재시 연락주세요");
		// member와 order중 연관관계의 주인은 order
		// member가 mappedBy를 가지고 있기 때문이다.
		// order.setMember(member);
		entityManager.persist(order);

		Order order2 = new Order();
		order2.setUuid(UUID.randomUUID().toString());
		order2.setOrderDatetime(LocalDateTime.now());
		order2.setOrderStatus(OPENED);
		order2.setMemo("부재시 2번째인 여기로 연락주세요");
		// order2.setMember(member);
		entityManager.persist(order2);

		// 하지만 편의메소드(member의 addOrder)로 양방향 매칭을 해줄 수 있다.
		Member member = new Member();
		member.setName("suyeon");
		member.setNickName("nicksuyeon");
		member.setAge(24);
		member.setAddress("daegu");

		member.addOrder(order);
		member.addOrder(order2);

		entityManager.persist(member);

		// 저장 확인
		List<Order> orders = member.getOrders();
		for (Order o : orders) {
			log.info("{}", o.toString()); // 위에서 저장된 order 2개나옴
		}

		transaction.commit();
	}

	@Test
	void 객체그래프탐색을_이용한_조회() {
		transaction.begin();

		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderDatetime(LocalDateTime.now());
		order.setOrderStatus(OPENED);
		order.setMemo("부재시 연락주세요");

		entityManager.persist(order);

		// 회원 엔티티
		Member member = new Member();
		member.setName("suyeon");
		member.setNickName("sujjang");
		member.setAge(24);
		member.setAddress("daegu");

		entityManager.persist(member);

		member.addOrder(order); // 연관관계 편의 메소드 사용 (member에서  주인인 order 메소드 호출)

		entityManager.persist(member);

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

		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderDatetime(LocalDateTime.now());
		order.setOrderStatus(OPENED);
		order.setMemo("부재시 연락주세요");

		order.setCreatedAt(LocalDateTime.now());
		order.setCreatedBy("suyeonjang");

		entityManager.persist(order);

		transaction.commit();
	}

}
