package com.kdt.lecturejpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class PersistenceContextTest {

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

		Customer customer = new Customer(); // 비 영속 상태
		customer.changeFirstName("honggu");
		customer.changeLastName("kang");

		entityManager.persist(customer); // 비 영속 -> 영속 (영속화)
		transaction.commit(); // entityManager.flush();
	}

	@Test
	void 조회_DB조회() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer();
		customer.changeLastName("gorani");
		customer.changeFirstName("byeong");

		entityManager.persist(customer);
		transaction.commit();

		entityManager.detach(customer); // 영속 -> 준영속

		// Hibernate: select c1_0.id,c1_0.first_name,c1_0.last_name from customers c1_0 where c1_0.id=?
		// 준영속 상태이기 때문에 select 를 이용하여 customer 를 들고온다.
		Customer selected = entityManager.find(Customer.class, customer.getId());
		log.info("{} {}", selected.getFirstName(), selected.getLastName());
	}

	@Test
	void 조회_1차캐시_이용() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer();
		customer.changeLastName("gorani");
		customer.changeFirstName("byeong");

		entityManager.persist(customer);
		transaction.commit();

		// select 쿼리가 사용되지 않는다.
		Customer selected = entityManager.find(Customer.class, customer.getId());
		log.info("{} {}", selected.getFirstName(), selected.getLastName());
	}

	@Test
	void 수정() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer();
		customer.changeLastName("gorani");
		customer.changeFirstName("byeong");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();
		customer.changeFirstName("guppy");
		customer.changeLastName("hong");

		// 변경 감지가 되어 다음 쿼리 실행
		// Hibernate: update customers set first_name=?,last_name=? where id=?
		transaction.commit();
	}

	@Test
	void 삭제() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer();
		customer.changeLastName("gorani");
		customer.changeFirstName("byeong");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();

		entityManager.remove(customer);

		// 변경 감지가 되어 다음 쿼리 실행
		// Hibernate: update customers set first_name=?,last_name=? where id=?
		transaction.commit();
	}
}
