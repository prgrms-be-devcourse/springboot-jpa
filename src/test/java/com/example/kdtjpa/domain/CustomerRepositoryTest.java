package com.example.kdtjpa.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.InputMismatchException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;

	@Test
	void insert_customer_test() {
		// Given
		Customer customer = new Customer(1L, "suyeon", "jang");

		// when
		repository.save(customer);

		// then
		Customer entity = repository.findById(1L).get();
		log.info("{} {}", entity.getLastName(), entity.getFirstName());

		assertThat(entity.getId()).isEqualTo(1L);
		assertThat(entity.getFirstName()).isEqualTo(customer.getFirstName());
		assertThat(entity.getLastName()).isEqualTo(customer.getLastName());
	}

	@Test
	void findById_customer_test() {
		// Given
		Customer customer = new Customer(1L, "suyeon", "jang");
		repository.save(customer);

		// When
		Customer selected = repository.findById(customer.getId()).get();

		// Then
		assertThat(customer.getId()).isEqualTo(selected.getId());
	}

	@Test
	void findAll_customer_test() {
		// Given
		Customer customer1 = new Customer(1L, "suyeon", "jang");
		Customer customer2 = new Customer(2L, "suJJang", "OvO");

		repository.saveAll(Lists.newArrayList(customer1, customer2));

		// When
		List<Customer> customerList = repository.findAll();

		// Then
		assertThat(customerList.size()).isEqualTo(2);
	}

	@Test
	void update_customer_test() {
		// Given
		Customer customer = new Customer(1L, "suyeon", "jang");
		repository.save(customer);

		// when
		Customer entity = repository.findById(1L).get();
		entity.setFirstName("OvO");
		entity.setLastName("suJJang");

		// then
		Customer updatedEntity = repository.findById(1L).get();
		log.info("{} {}", updatedEntity.getLastName(), updatedEntity.getFirstName());

		assertThat(updatedEntity.getId()).isEqualTo(1L);
		assertThat(updatedEntity.getFirstName()).isEqualTo(entity.getFirstName());
		assertThat(updatedEntity.getLastName()).isEqualTo(entity.getLastName());
	}

	@Test
	void delete_customer_test() {
		// Given
		Customer customer = new Customer(1L, "suyeon", "jang");
		Customer customer2 = new Customer(2L, "first", "last");
		Customer customer3 = new Customer(3L, "first", "last");
		repository.saveAll(Lists.newArrayList(customer, customer2, customer3));

		// when
		repository.deleteById(1L);

		// then
		List<Customer> customerList = repository.findAll();
		assertThat(customerList.size()).isEqualTo(2);
	}

	@Test
	void validate_customerName_test() {
		// Given
		Customer customer = new Customer(1L, "testCustomer", "testCustomer");

		// when
		repository.save(customer);

		// then
		Customer entity = repository.findById(1L).get();
		assertThat(entity).usingRecursiveComparison().isEqualTo(customer);
	}

	@Test
	void inValidate_customerName_test() {
		// Given & When & Then
		assertThatThrownBy(() -> new Customer(1L, "12!!", "34!!")).isInstanceOf(InputMismatchException.class)
			.hasMessageContaining("Not validatedName");
	}
}
