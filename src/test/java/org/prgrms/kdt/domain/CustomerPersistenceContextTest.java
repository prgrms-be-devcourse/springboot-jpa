package org.prgrms.kdt.domain;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CustomerPersistenceContextTest {

	@Autowired
	private EntityManagerFactory emf;

	Customer newCustomer = new Customer("moly", "holy");

	@Test
	@DisplayName("customer 객체가 영속성 컨텍스트에서 관리된다.")
	void testPersist() {
	    // given
		EntityManager em = emf.createEntityManager();

		// when
		em.persist(newCustomer);

	    // then
		assertThat(em.contains(newCustomer), is(true));
	}

	@Test
	@DisplayName("detach() 사용 → customer 객체가 준영속 상태가 된다.")
	void testDetach() {
		// given
		EntityManager em = emf.createEntityManager();
		em.persist(newCustomer);

		// when
		em.detach(newCustomer);

		// then
		assertThat(em.contains(newCustomer), is(false));
	}

	@Test
	@DisplayName("clear() 사용 → customer 객체가 준영속 상태가 된다.")
	void testClear() {
		// given
		EntityManager em = emf.createEntityManager();
		em.persist(newCustomer);

		// when
		em.clear();

		// then
		assertThat(em.contains(newCustomer), is(false));
	}

	@Test
	@DisplayName("close() 사용 → customer 객체가 준영속 상태가 된다.")
	void testClose() {
		// given
		EntityManager em = emf.createEntityManager();
		em.persist(newCustomer);

		// when
		em.close();

		// then
		assertThat(em.isOpen(), is(false));

		// ↓ em 이미 닫혔다는 오류 발생 → java.lang.IllegalStateException: Session/EntityManager is closed
		//assertThat(em.contains(newCustomer), is(false));
	}
	
	@Test
	@DisplayName("remove() 사용 → customer 객체가 영속성 컨텍스트 분리, DB에서 삭제된다.")
	void testRemove() {
		// given
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(newCustomer);

		transaction.commit();

	    // when
		em.remove(newCustomer);

	    // then
		log.info("Removed customer : {}", newCustomer);
		assertThat(em.contains(newCustomer), is(false));

		Customer removedCustomer = em.find(Customer.class, newCustomer.getId());
		assertThat(removedCustomer, nullValue());
	}
	
	@Test
	@DisplayName("1차 캐시에서 조회한다.")
	void testSerachFrom1thCache() {
	    // given
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newCustomer);
		transaction.commit();
	    
	    // when
		Customer foundCustomer = em.find(Customer.class, newCustomer.getId());

		// then
		log.info("Found customer: {}", foundCustomer);
		assertThat(foundCustomer, is(newCustomer));
	}

	@Test
	@DisplayName("1차 캐시가 아닌 DB에서 조회한다.")
	void testSearchFromDatabase() {
		// given
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newCustomer);
		transaction.commit();

		// when
		em.clear();

		// then
		assertThat(em.contains(newCustomer), is(false));

		// 준영속 상태에서 찾게 되면, DB를 통해서 새로운 객체(<> 기존 준영속 객체)가 영속화 된다.
		Customer foundCustomer = em.find(Customer.class, newCustomer.getId());

		// 각 필드 값은 같지만, 다른 객체(메모리 주소가 다름)이다.
		log.info("newCustomer : {} / foundCustomer: {}", newCustomer.hashCode(), foundCustomer.hashCode());
		assertThat(foundCustomer, samePropertyValuesAs(newCustomer));
		assertThat(foundCustomer, not(newCustomer));

		assertThat(em.contains(newCustomer), is(false)); 	// 기존 객체는 준영속 상태
		assertThat(em.contains(foundCustomer), is(true)); // 새로운 객체가 영속 상태

		// 🤔 준영속 상태를 remove 하게 되면?
		// → 오류 발생 [java.lang.IllegalArgumentException: Removing a detached instance org.prgrms.kdt.domain.Customer#58]
		//   remove()는 영속 관리되는 객체만 가능
		assertThrows(IllegalArgumentException.class, () -> {
			em.remove(newCustomer);
		});
	}
	
	@Test
	@DisplayName("customer 정보가 dirty checking으로 수정된다.")
	void testUpdateByDirtyChecking() {
		// given
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newCustomer);
		transaction.commit();

		transaction.begin();

	    // when
		newCustomer.changeLastName("dooly");
		newCustomer.changeFirstName("hoit");
		transaction.commit();

	    // then
		Customer updatedCustomer = em.find(Customer.class, newCustomer.getId());
		assertThat(updatedCustomer, samePropertyValuesAs(newCustomer));
	}

	@Test
	@DisplayName("customer가 삭제된다.")
	void testDelete() {
	    // given
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newCustomer);
		transaction.commit();

		transaction.begin();

		Customer foundCustomer = em.find(Customer.class, newCustomer.getId());

		// when
		em.remove(foundCustomer);
		transaction.commit();
	
	    // then
		Customer deletedCustomer = em.find(Customer.class, newCustomer.getId());
		assertThat(deletedCustomer, nullValue());
	}
}