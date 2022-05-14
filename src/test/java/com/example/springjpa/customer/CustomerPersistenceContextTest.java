package com.example.springjpa.customer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@DisplayName("기본키 생성 전략이 SEQUENCE 일 때 EntityManager 를 사용하여 테스트 한다")
@Slf4j
public class CustomerPersistenceContextTest {

	@Autowired
	private EntityManagerFactory emf;

	private EntityManager em;
	private EntityTransaction tx;
	private Customer customer;

	@BeforeEach
	void setUp() {
		customer = createCustomer();
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	@Test
	@DisplayName("new 상태의 엔티티를 persist 하면 커밋 시 insert 된다")
	public void persist_given_customer() {
		tx.begin();
		em.persist(customer);
		tx.commit();
	}

	@Test
	@DisplayName("new 상태의 엔티티를 merge 하면 커밋 시 insert 된다")
	public void given_newEntity_when_merge_then_nothingHappen() {
		tx.begin();

		em.merge(customer);

		tx.commit();
	}

	@Test
	@DisplayName("merge 파라미터로 넘긴 엔티티를 변경하면 변경감지가 일어나지 않는다")
	public void given_entityPassedByParameterToMerge_when_changeThatEntity_then_nothingHappen() {
		tx.begin();

		em.merge(customer);
		customer.changeFirstName("kim");

		tx.commit();
	}

	@Test
	@DisplayName("merge 결과로 리턴되어온 엔티티를 변경하면 변경감지가 일어난다")
	public void given_entityReturnedByMerge_when_changeThatEntity_then_update() {
		tx.begin();

		Customer persistedCustomer = em.merge(customer);
		persistedCustomer.changeFirstName("kim");

		tx.commit();
	}

	@Test
	@DisplayName("영속화 했던 엔티티를 detach 하면 변경감지가 일어나지 않는다")
	public void given_mergedEntity_when_detach_then_flushNotHappen() {
		tx.begin();

		Customer persistedCustomer = em.merge(customer);
		em.detach(persistedCustomer);
		persistedCustomer.changeFirstName("kim");

		tx.commit();
	}

	@Test
	@DisplayName("DB 에 저장되어 있는 엔티티에 대한 프록시 객체를 가져와 값을 변경하는 경우, 객체의 초기화가 일어나고 값이 변경된다")
	public void given_proxy_when_change_then_initHappen() {
		tx.begin();

		// DB 에 저장 _insert
		em.persist(customer);
		em.flush();
		// 영속성 컨텍스트를 비운다
		em.clear();

		// proxy
		Customer proxy = em.getReference(Customer.class, customer.getId());
		log.info("Proxy 를 가져옴");

		proxy.changeLastName("kim"); // 엔티티 초기화 - select

		tx.commit();
	}

	@Test
	@DisplayName("JPQL 쿼리를 날려 firstName 이 abc 로 시작하는 모든 커스토머를 가져와 그 개수를 확인한다")
	public void given_customersName_when_findAllCustomers_then_success() {
		tx.begin();

		// 하나의 커스토머를 DB 에 저장 : INSERT
		em.persist(customer);
		em.flush();
		// 영속성 컨텍스트를 비운다
		em.clear();

		// JPQL : SELECT
		List<Customer> customers = em.createQuery("select c from Customer c where c.firstName like '%abc'",
				Customer.class)
			.getResultList();

		Assertions.assertThat(customers.size()).isEqualTo(1);

		tx.commit();
	}

	@Test
	@DisplayName("DB 에 저장되어 있지 않은 엔티티에 대한 프록시 객체를 가져와 값을 변경하는 경우 객체의 초기화가 일어나며 예외가 발생한다")
	public void given_proxy_when_change_then_exceptionThrown() {
		tx.begin();
		// sequence -> customer 인스턴스에 id 를 부여
		em.persist(customer);
		// 영속성 컨텍스트를 비우고 ( flush x 상태 에서 영속성 컨텍스트를 비워버림 )
		em.clear();
		// 프록시 객체 얻어오기 ( customer 는 id 를 갖고 있다 )
		Customer proxy = em.getReference(Customer.class, customer.getId());

		log.info("Proxy 객체를 얻어옴");
		// Entity 초기화 - DB 에는 저장되지 않았었기 때문에 예외가 발생한다
		Assertions.assertThatThrownBy(() ->
				proxy.changeFirstName("kim"))
			.isInstanceOf(EntityNotFoundException.class);

		tx.commit();
	}

	@Test
	@DisplayName("find 를 통해 DB 로부터 영속화 된 엔티티를 찾아온다")
	public void given_idAndType_when_find_then_success() {
		tx.begin();

		em.persist(customer);
		em.flush();
		em.clear();

		Customer foundCustomer = em.find(Customer.class, this.customer.getId());

		Assertions.assertThat(foundCustomer)
			.usingRecursiveComparison()
			.isEqualTo(this.customer);

		tx.commit();
	}

	private Customer createCustomer() {
		return new Customer("abc", "mart");
	}
}
