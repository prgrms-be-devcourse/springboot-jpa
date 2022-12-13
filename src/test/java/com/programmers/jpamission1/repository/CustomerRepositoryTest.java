package com.programmers.jpamission1.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

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
	void saveAndFindSuccessTest() {

		//given
		Customer customer = new Customer("geonwoo", "Lee");

		//when
		Customer savedCustomer = customerRepository.save(customer);

		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(customer.getId());
		assertThat(maybeCustomer).isPresent();
		assertThat(maybeCustomer.get()).isEqualTo(savedCustomer);

	}

	@Test
	@DisplayName("고객 저장을 실패한다.")
	void saveFailTest() {

		//given
		Customer customer = null;

		//when & then
		assertThatThrownBy(() -> customerRepository.save(customer)).isInstanceOf(Exception.class);

	}

	@Test
	@DisplayName("고객 조회를 실패한다.")
	void findFailTest() {

		//given
		Long id = null;

		//when & then
		assertThatThrownBy(() -> customerRepository.findById(id)).isInstanceOf(Exception.class);

	}

	@Test
	@DisplayName("고객의 firstName,lastName을 수정하고 수정된 결과를 조회한다 - 성공")
	void updateSuccessTest() {

		//given
		Customer customer = new Customer("geonwoo", "Lee");
		Customer savedCustomer = customerRepository.save(customer);

		//when
		savedCustomer.updateFullName("Derrick", "Rose");

		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(savedCustomer.getId());
		assertThat(maybeCustomer).isPresent();
		assertThat(maybeCustomer.get().getFirstName()).isEqualTo(savedCustomer.getFirstName());
		assertThat(maybeCustomer.get().getLastName()).isEqualTo(savedCustomer.getLastName());

	}

	@Test
	@DisplayName("고객의 firstName,lastName을 수정하고 수정된 결과를 조회한다 - 성공")
	void updateFailTest() {

		//given
		Customer customer = new Customer("geonwoo", "Lee");
		Customer savedCustomer = customerRepository.save(customer);

		//when
		savedCustomer.updateFullName("Derrick11", "Rose11111");

		//then
		assertThatThrownBy(() -> customerRepository.flush()).isInstanceOf(Exception.class);

	}

	@Test
	@DisplayName("고객을 삭제하고 삭제된 결과를 확인한다. - 성공")
	void deleteSuccessTest() {

		//given
		Customer customer = new Customer("geonwoo", "Lee");
		Customer savedCustomer = customerRepository.save(customer);

		//when
		customerRepository.deleteById(savedCustomer.getId());
		customerRepository.flush();
		//then
		Optional<Customer> maybeCustomer = customerRepository.findById(customer.getId());
		assertThat(maybeCustomer).isEmpty();

	}

	@Test
	@DisplayName("고객을 삭제하고 삭제된 결과를 확인한다. - 실패")
	void deleteFailTest() {

		//given
		Long id = null;

		//when & then
		assertThatThrownBy(() -> customerRepository.deleteById(id)).isInstanceOf(Exception.class);

	}

}