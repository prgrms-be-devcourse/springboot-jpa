package com.programmers.app.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository repository;

	@Autowired
	private EntityManager em;

	@Test
	void testInsert() {
		//given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("hoggu");
		customer.setLastName("kang");
		//when
		Customer savedCustomer = repository.save(customer);

		Optional<Customer> foundEntity = repository.findById(1L);

		//then
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get()).isEqualTo(savedCustomer);
	}

	@Test
	void testLookUp() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("hoggu");
		customer.setLastName("kang");
		Customer savedCustomer = repository.save(customer);

		//when
		Optional<Customer> foundEntity = repository.findById(1L);

		//then
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get().getFirstName()).isEqualTo(savedCustomer.getFirstName());
		assertThat(foundEntity.get().getLastName()).isEqualTo(savedCustomer.getLastName());
	}

	@Test
	void testUpdate() {
		//given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("hoggu");
		customer.setLastName("kang");
		Customer savedCustomer = repository.save(customer);

		//when
		customer.setFirstName("jinhyung");
		customer.setLastName("park");
		Optional<Customer> foundEntity = repository.findById(customer.getId());

		//then
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get()).isEqualTo(savedCustomer);
	}

	@Test
	void testLookUpOne() {
		//given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("hoggu");
		customer.setLastName("kang");
		Customer savedCustomer = repository.save(customer);

		//when
		repository.delete(savedCustomer);
		Optional<Customer> foundEntity = repository.findById(1L);

		//then
		assertThat(foundEntity).isEmpty();
	}
}