package com.programmers.jpapractice.domain;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EntityLifeCycleTest {

	@Autowired
	EntityManagerFactory emf;

	@Test
	@DisplayName("엔티티가 비영속 상태이다.")
	void unmanagedTest() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer customer = new Customer("권", "성준");
		tx.commit();

		assertThat(em.contains(customer)).isFalse();
	}

	@Test
	@DisplayName("엔티티가 영속 상태이다.")
	void managedTest() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer customer = new Customer("권", "성준");
		em.persist(customer);
		tx.commit();

		assertThat(em.contains(customer)).isTrue();
	}

	@Test
	@DisplayName("엔티티가 준영속 상태이다.")
	void detachedTest() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer customer = new Customer("권", "성준");
		em.persist(customer);
		em.detach(customer);
		tx.commit();

		assertThat(em.contains(customer)).isFalse();
	}

	@Test
	@DisplayName("엔티티가 삭제 상태이다.")
	void removedTest() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Customer customer = new Customer("권", "성준");
		em.persist(customer);
		em.remove(customer);
		tx.commit();

		assertThat(em.contains(customer)).isFalse();
	}
}
