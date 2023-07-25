package com.kdt.lecturejpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository repository;

	@Test
	void test() {
		// Given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("honggu");
		customer.setLastName("kang");

		// When
		repository.save(customer);

		// Then
		Customer entity = repository.findById(1L).get();
		log.info("{} {}", entity.getLastName(), entity.getFirstName());
	}
}