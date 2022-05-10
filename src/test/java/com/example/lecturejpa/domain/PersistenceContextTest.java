package com.example.lecturejpa.domain;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
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
	void 단건_저장_테스트() {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("jinhyung");
		customer.setLastName("park");

		entityManager.persist(customer);
		transaction.commit();

		assertThat(entityManager.contains(customer)).isTrue();
	}

	@Test
	void 준영속_상태_FIND_테스트() {
		//givn
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Long customerId = 1L;

		//when
		transaction.begin();

		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setFirstName("jinhyung");
		customer.setLastName("park");

		entityManager.persist(customer);
		transaction.commit();

		entityManager.detach(customer);

		//then
		assertThat(entityManager.contains(customer)).isFalse();
		Customer foundCustomer = entityManager.find(Customer.class, customerId);
		log.info("{} {}", foundCustomer.getFirstName(), foundCustomer.getLastName());
	}

	@Test
	void 영속_상태_FIND_테스트() {
		//given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Long customerId = 1L;

		//when
		transaction.begin();

		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setFirstName("jinhyung");
		customer.setLastName("park");

		entityManager.persist(customer);
		transaction.commit();

		//then
		Customer foundCustomer = entityManager.find(Customer.class, customerId);
		log.info("{} {}", foundCustomer.getFirstName(), foundCustomer.getLastName());
	}

	@Test
	void 더티_체킹_수정_테스트() {
		//given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Long customerId = 1L;
		String updatedFirstName = "jinh";
		String updatedLastName = "park";

		//when
		transaction.begin();

		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setFirstName("jinhyung");
		customer.setLastName("park");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();
		customer.setFirstName(updatedFirstName);
		customer.setLastName(updatedLastName);
		transaction.commit();

		//then
		Customer foundCustomer = entityManager.find(Customer.class, customerId);
		assertThat(foundCustomer).isNotNull();
		assertThat(foundCustomer).isEqualTo(customer);
		assertThat(foundCustomer.getLastName()).isEqualTo(updatedLastName);
		assertThat(foundCustomer.getFirstName()).isEqualTo(updatedFirstName);
	}

	@Test
	void 비영속_상태로_만들기_테스트() {
		//given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Long customerId = 1L;
		String updatedFirstName = "jinh";
		String updatedLastName = "park";

		//when
		transaction.begin();

		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setFirstName("jinhyung");
		customer.setLastName("park");

		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();
		entityManager.detach(customer);
		transaction.commit();

		//then
		assertThat(entityManager.contains(customer)).isFalse();
	}
}
