package com.kdt.lecturejpa.domain;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository repository;

	private Customer newCustomer;

	@BeforeEach
	void setUp() {
		newCustomer = new Customer();
		newCustomer.changeFirstName("byeonggon");
		newCustomer.changeLastName("kang");
	}

	@Test
	@DisplayName("Customer를 데이터베이스에 저장할 수 있다.")
	void saveTest() {
		// When
		Customer actualCustomer = repository.save(newCustomer);

		// Then
		assertThat(actualCustomer, is(samePropertyValuesAs(newCustomer)));
	}

	@Test
	@DisplayName("Customer를 조회할 수 있다.")
	void findTest() {
		// Given
		Customer savedCustomer = repository.save(newCustomer);

		// When
		Customer foundCustomer = repository.findById(savedCustomer.getId()).orElse(null);

		// Then
		assertThat(foundCustomer, is(samePropertyValuesAs(savedCustomer)));
	}

	@Test
	@DisplayName("Customer를 수정할 수 있다.")
	void updateTest() {
		// Given
		Customer savedCustomer = repository.save(newCustomer);

		// When
		newCustomer.changeFirstName("byeong");
		newCustomer.changeLastName("gorani");
		Customer updatedCustomer = repository.save(savedCustomer);

		// Then
		assertThat(updatedCustomer, is(samePropertyValuesAs(savedCustomer)));
	}

	@Test
	@DisplayName("Customer를 삭제할 수 있다.")
	void removeTest() {
		// Given
		Customer savedCustomer = repository.save(newCustomer);

		// When
		repository.delete(savedCustomer);

		// Then
		Optional<Customer> mayFoundCustomer = repository.findById(newCustomer.getId());
		assertThat(mayFoundCustomer.isEmpty(), is(true));
	}
}