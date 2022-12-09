package com.programmers.jpamission1.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpamission1.domain.Customer;


@DataJpaTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	@DisplayName("고객을 저장하고 저장된 결과를 조회한다 - 성공")
	void saveTest() {

		//given
		Customer customer = new Customer(1L, "geonwoo", "Lee");

		//when
		Customer savedCustomer = customerRepository.save(customer);

		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(customer.getId());
		Assertions.assertThat(maybeCustomer).isPresent();
		Assertions.assertThat(maybeCustomer.get()).isEqualTo(savedCustomer);

	}

	@Test
	@DisplayName("고객의 이름을 수정하고 수정된 결과를 조회한다 - 성공")
	void updateTest() {

		//given
		Customer customer = new Customer(1L, "geonwoo", "Lee");
		Customer savedCustomer = customerRepository.save(customer);

		//when
		savedCustomer.updateFullName("Derrick","Rose");

		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(savedCustomer.getId());
		Assertions.assertThat(maybeCustomer).isPresent();
		Assertions.assertThat(maybeCustomer.get()).isEqualTo(savedCustomer);

	}

	@Test
	@DisplayName("고객을 삭제하고 삭제된 결과를 확인한다. - 성공")
	void deleteTest() {

		//given
		Customer customer = new Customer(1L, "geonwoo", "Lee");
		Customer savedCustomer = customerRepository.save(customer);

		//when
		customerRepository.deleteById(savedCustomer.getId());

		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(customer.getId());
		Assertions.assertThat(maybeCustomer).isEmpty();

	}
}