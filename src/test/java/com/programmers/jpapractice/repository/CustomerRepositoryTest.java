package com.programmers.jpapractice.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpapractice.repository.domain.Customer;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;

	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}

	@Test
	@DisplayName("올바른 고객 정보로 저장을 하면 저장에 성공한다.")
	void customerSaveTest() {
		Customer customer = new Customer("권", "성준");

		Customer savedCustomer = repository.save(customer);

		assertThat(savedCustomer).isEqualTo(customer);
	}

	@Test
	@DisplayName("고객 id로 조회하면 조회에 성공한다.")
	void customerFindTest() {
		// given
		Customer customer = new Customer("권", "성준");
		Customer savedCustomer = repository.save(customer);

		// when
		Optional<Customer> optionalCustomer = repository.findById(savedCustomer.getId());
		assertThat(optionalCustomer).isPresent();

		Customer findCustomer = optionalCustomer.get();

		// then
		assertThat(findCustomer).isEqualTo(savedCustomer);
	}

	@Test
	@DisplayName("고객 정보 수정에 성공한다.")
	void customerUpdateTest() {
		// given
		Customer customer = new Customer("권", "성준");
		Customer savedCustomer = repository.save(customer);

		// when
		String changeFirstName = "김";
		String changeLastName = "성중";
		savedCustomer.changeFullName(changeFirstName, changeLastName);
		Optional<Customer> optionalUpdatedCustomer = repository.findById(savedCustomer.getId());
		assertThat(optionalUpdatedCustomer).isPresent();

		Customer findCustomer = optionalUpdatedCustomer.get();

		// then
		assertThat(findCustomer.getFirstName()).isEqualTo(changeFirstName);
		assertThat(findCustomer.getLastName()).isEqualTo(changeLastName);
	}

	@Test
	@DisplayName("고객을 삭제하면 삭제에 성공한다.")
	void customerDeleteTest() {
		// given
		Customer customer = new Customer("권", "성준");
		Customer savedCustomer = repository.save(customer);

		// when
		repository.delete(savedCustomer);
		Optional<Customer> optionalFindCustomer = repository.findById(savedCustomer.getId());

		// then
		assertThat(optionalFindCustomer).isEmpty();
	}
}