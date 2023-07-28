package com.devcourse.springbootjpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devcourse.springbootjpa.domain.Customer;

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

		Customer customer = new Customer("Seungwon", "Han");

		entityManager.persist(customer);
		transaction.commit();
	}

	@Test
	void 조회_DB조회() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer("Seungwon", "Han");

		entityManager.persist(customer);
		transaction.commit();
		long id = customer.getId();

		entityManager.detach(customer);

		Customer selected = entityManager.find(Customer.class, id);
		log.info("{} {}", selected.getFirstName(), selected.getLastName());
	}

	@Test
	void 조회_1차캐시_조회() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer("Seungwon", "Han");

		entityManager.persist(customer);
		transaction.commit();
		long id = customer.getId();

		Customer selected = entityManager.find(Customer.class, id);
		log.info("{} {}", selected.getFirstName(), selected.getLastName());
	}
	@Test
	void 수정() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer("Seungwon", "Han");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();
		customer.changeFirstName("Gildong");
		customer.changeLastName("Hong");

		transaction.commit();
	}

	@Test
	void 삭제() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer("Seungwon", "Han");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();

		entityManager.remove(customer);

		transaction.commit();
	}
}
