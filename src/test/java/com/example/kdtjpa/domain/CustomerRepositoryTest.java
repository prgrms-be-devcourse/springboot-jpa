package com.example.kdtjpa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;

	@Test
	void test() {
		// Given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("suyeon");
		customer.setLastName("jang");

		// when
		repository.save(customer);

		// then
		Customer entity = repository.findById(1L).get();
		log.info("{} {}", entity.getLastName(), entity.getFirstName());
	}
}
