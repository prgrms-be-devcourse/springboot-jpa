package com.study.springbootJpa.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	private final Customer customer1 = new Customer(1L, "jjin", "bbang");
	private final Customer customer2 = new Customer(2L, "ho", "bbang");

	@BeforeEach
	void setUp() {
		customerRepository.save(customer1);
		customerRepository.save(customer2);
	}

	@Test
	@DisplayName("Customer가 저장되어야함")
	void save_test() {
		//given
		var saveId = 3L;
		var customer = new Customer(saveId, "hyeb", "park");
		//when
		var savedCustomer = customerRepository.save(customer);
		var foundCustomer = customerRepository.findById(saveId);
		//then
		assertThat(foundCustomer).isPresent();
		assertThat(savedCustomer).isEqualTo(foundCustomer.get());
	}

	@Test
	@DisplayName("모든 Customer의 List가 return 되어야함 ")
	void findAll_test() {
		//given
		List<Customer> customers = List.of(customer1, customer2);
		//when
		var foundCustomers = customerRepository.findAll();
		//then
		assertThat(foundCustomers.size()).isEqualTo(customers.size());
		assertThat(foundCustomers).isEqualTo(customers);
	}

	@Test
	@DisplayName("해당 Id의 Customer가 return 되어야함")
	void findById_test() {
		//given
		var id = customer1.getId();
		//when
		var foundCustomer = customerRepository.findById(id);
		//then
		assertThat(foundCustomer).isPresent();
		assertThat(id).isEqualTo(foundCustomer.get().getId());
	}

	@Test
	@DisplayName("없는 Id 조회시 Empty가 반환되어야함")
	void findById_fail_test() {
		//given
		var id = 100L;
		//when
		var foundCustomer = customerRepository.findById(id);
		//then
		assertThat(foundCustomer).isEmpty();
	}

	@Test
	@DisplayName("해당 Id의 Customer가 삭제 되어야함")
	void deleteById_test() {
		//given
		var id = customer1.getId();
		//when
		customerRepository.deleteById(id);
		//then
		var deletedCustomer = customerRepository.findById(id);
		assertThat(deletedCustomer).isEmpty();
	}

	@Test
	@DisplayName("Customer의 FirstName과 LastName이 수정되어야함")
	void update_test() {
		//given
		var id = 1L;
		var foundCustomer = customerRepository.findById(id).get();
		foundCustomer.update("update", "test");
		//when
		var updatedCustomer = customerRepository.findById(id);
		//then
		assertThat(updatedCustomer).isPresent();
		assertThat(updatedCustomer.get()).isEqualTo(foundCustomer);
	}
}