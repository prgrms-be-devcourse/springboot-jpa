package com.programmers.jpamission1.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.programmers.jpamission1.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class EntityLifeCycleTest {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EntityManagerFactory emf;

	EntityManager em;

	EntityTransaction tx;

	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	Customer persistCustomer() {
		tx.begin();
		Customer customer = new Customer();
		customer.updateFullName("geonwoo", "Lee");
		em.persist(customer);
		return customer;
	}

	@Test
	@DisplayName("영속 상태 테스트")
	void managedTest() {

		Customer customer = persistCustomer();
		tx.commit();

		Assertions.assertThat(em.contains(customer)).isTrue();

	}

	@Test
	@DisplayName("삭제 상태 테스트")
	void removedTest() {

		Customer customer = persistCustomer();
		em.remove(customer);
		tx.commit();

		Assertions.assertThat(em.contains(customer)).isFalse();

	}

	@Test
	@DisplayName("준영속 상태 테스트")
	void detachedTest() {

		Customer customer = persistCustomer();
		em.detach(customer);
		tx.commit();

		Assertions.assertThat(em.contains(customer)).isFalse();

	}

	@Test
	@DisplayName("동일성 테스트")
	void identityTest() {

		Customer customer = persistCustomer();
		tx.commit();

		Customer customer1 = em.find(Customer.class, customer.getId());
		Customer customer2 = em.find(Customer.class, customer.getId());

		Assertions.assertThat(customer1).isEqualTo(customer2);

	}

	@Test
	@DisplayName("변경 감지 테스트")
	void dirtyCheckTest() {

		String firstName = "Derrick";
		String lastName = "Rose";
		Customer customer = persistCustomer();

		customer.updateFullName(firstName, lastName);
		tx.commit();
		Customer findCustomer = em.find(Customer.class, customer.getId());

		Assertions.assertThat(findCustomer.getFirstName()).isEqualTo(firstName);
		Assertions.assertThat(findCustomer.getLastName()).isEqualTo(lastName);

	}

}
