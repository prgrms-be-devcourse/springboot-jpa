package com.study.springbootJpa;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.springbootJpa.repository.Customer;
import com.study.springbootJpa.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

	@Autowired
	CustomerRepository repository;

	@Autowired
	private EntityManagerFactory managerFactory;
	Customer customer = new Customer();

	@BeforeEach
	void setUp() {
		customer.setId(1L);
		customer.setFirstName("hyeb");
		customer.setLastName("park");

		log.info("repository deleteAll 실행으로 날아간 query");
		repository.deleteAll();
	}

	@Test
	@DisplayName("쓰기지연 테스트")
	void context_test1() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();

		log.info("persist시 쓰기 지연으로 query 안 날아감");
		entityManager.persist(customer);

		log.info("commit 실행");
		transaction.commit();
	}

	@Test
	@DisplayName("영속 상태 find시 캐시에서 조회")
	void context_managed_find() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(customer);

		log.info("영속상태 find 실행");
		entityManager.find(Customer.class, 1L);

		log.info("commit 실행");
		transaction.commit();
	}

	@Test
	@DisplayName("준영속 상태 find시 db에서 조회")
	void context_detach_find() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(customer);

		log.info("persist 후 commit 실행");
		transaction.commit();

		entityManager.detach(customer);

		log.info("준영속 상태 find 실행");
		entityManager.find(Customer.class, 1L);

		log.info("commit 실행");
		transaction.commit();
	}

	@Test
	@DisplayName("transaction 끝나도 context는 유지 되어 1차 캐시에서 조회됨")
	void context_commit() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(customer);

		log.info("커밋 전에 find customer");
		var beforeCommit = entityManager.find(Customer.class, 1L);

		log.info("commit 실행");
		transaction.commit();

		log.info("transaction 끝난 후 find customer");
		var afterCommit = entityManager.find(Customer.class, 1L);
		assertThat(afterCommit).isEqualTo(beforeCommit);
	}

	@Test
	@DisplayName("수정 변경 감지")
	void context_update() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(customer);
		log.info("persist customer -> {}{}", customer.getFirstName(), customer.getLastName());

		customer.setFirstName("udpate");
		customer.setLastName("test");

		var updatedCustomer
			= entityManager.find(Customer.class, 1L);
		log.info("commit 하기 전 update customer -> {}{}", updatedCustomer.getFirstName(), updatedCustomer.getLastName());

		log.info("commit 실행");
		transaction.commit();
	}

	@Test
	@DisplayName("삭제")
	void context_delete() {
		var entityManager = managerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(customer);
		log.info("persist 후에 commit 실행");
		transaction.commit();

		transaction.begin();

		log.info("remove 실행");
		entityManager.remove(customer);

		log.info("remove 후에 commit 실행");
		transaction.commit();
	}
}