package com.programmers.app.persistent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.programmers.app.domain.Customer;
import com.programmers.app.domain.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ContextTest {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EntityManagerFactory emf;

	static Long idAutoIncrement = 0L;
	Faker faker = new Faker();

	@BeforeEach
	void init() {
		idAutoIncrement++;
	}

	@Test
	@DisplayName("1차 캐시를 이용한 조회 - 1번의 insert query")
	void testFindOfPersistenceContext() {

		//given
		Customer customer = getCustomer();
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		//when
		entityManager.persist(customer);
		transaction.commit();
		Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

		//then
		Assertions.assertThat(foundCustomer).isNotNull();
	}

	@Test
	@DisplayName("업데이트 - [영속성 컨텍스트 dirty checking ] snap shot 변경 내용 추척후 update 쿼리 발생")
	void testUpdate() {
		//given

		Customer customer = getCustomer();

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(customer);
		transaction.commit();

		//when : 영속성 컨텍트스 스냅샷 변경내역 추척
		transaction.begin();
		customer.setFirstName(faker.name().firstName());
		customer.setLastName(faker.name().lastName());
		transaction.commit();

		Customer expectedUpdating = entityManager.find(Customer.class, customer.getId());
		//then
		MatcherAssert.assertThat(expectedUpdating, Matchers.samePropertyValuesAs(expectedUpdating));

	}

	@Test
	@DisplayName("조회 - [영속성 컨텍스트 초기화 후] 1차 캐시 이용x 쿼리 2번(insert,select)")
	void testFind() {
		//given

		Customer customer = getCustomer();

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		entityManager.persist(customer);
		transaction.commit();

		//when : 영속성 컨텍트스 init
		entityManager.clear();
		Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
		//then

		MatcherAssert.assertThat(customer, Matchers.samePropertyValuesAs(foundCustomer));
	}

	@Test
	@DisplayName("삭제 영속성 컨텍스트 remove")
	void testDelete() {
		//given
		Customer customer = getCustomer();
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		//when
		transaction.begin();
		entityManager.persist(customer);
		transaction.commit();

		transaction.begin();
		entityManager.remove(customer);
		transaction.commit();

		Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
		//then

		Assertions.assertThat(foundCustomer).isNull();
	}

	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(idAutoIncrement);
		customer.setFirstName(faker.name().firstName());
		customer.setFirstName(faker.name().lastName());
		return customer;
	}
}
