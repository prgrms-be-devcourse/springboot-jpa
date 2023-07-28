package com.example.kdtjpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PersistenceContextTest {

	@Autowired
	CustomerRepository repository;

	@Autowired
	EntityManagerFactory emf;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
	}

	@Test
	void 저장() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer(1L, "suyeon", "jang"); // 비영속상태

		entityManager.persist(customer); // 비영속 -> 영속 (영속화)
		transaction.commit(); // commit -> insert 쿼리 실행 -> entityManager.flush DB와 동기화
	}

	@Test
	void 조회_DB조회() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer(2L, "suJJang", "OvO");

		entityManager.persist(customer);
		transaction.commit();

		entityManager.detach(customer);
		entityManager.clear(); // 영속 -> 준영속, 영속성 컨텍스트 초기화

		Customer selected = entityManager.find(Customer.class, 2L); // 1차 캐시에 suJJang 없음, DB에서 조회
		log.info("{} {}", selected.getFirstName(), selected.getLastName());

		entityManager.find(Customer.class, 2L); // 이전에 DB에서 조회한 후 1차캐시에 저장했기에 1차캐시에서 조회
	}

	@Test
	void 조회_1차캐시_이용() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer(1L, "suyeon", "jang"); // 비영속상태

		entityManager.persist(customer); // 비영속 -> 영속 (영속화)
		transaction.commit(); // entityManager.flush();

		Customer selected = entityManager.find(Customer.class, 1L); // select 쿼리 수행 X, 1차 캐시에서 바로 조회
		log.info("{} {}", selected.getFirstName(), selected.getLastName());
	}

	@Test
	void 수정() { // dirty checking
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer(1L, "suyeon", "jang");

		entityManager.persist(customer); // 영속화
		transaction.commit(); // entityManager.flush();

		transaction.begin();
		customer.setFirstName("suJJang");
		customer.setLastName("OvO"); // 고객 정보 변경

		transaction.commit(); // update 쿼리문을 사용자가 하지 않아도 flush 시점에 스냅샷과 영속상태 엔티티를 비교해서 변경 유무를 확인한다.
		// 만약 변경이 감지된다면 자동으로 update 쿼리를 날려준다
	}

	@Test
	void 삭제() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer(1L, "suyeon", "jang");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();

		Customer entity = entityManager.find(Customer.class, 1L);
		entityManager.remove(entity);

		transaction.commit(); // flush 이후 delete
	}

}