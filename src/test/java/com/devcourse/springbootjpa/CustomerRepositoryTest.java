package com.devcourse.springbootjpa;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devcourse.springbootjpa.domain.Customer;

@DataJpaTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Nested
	@DisplayName("고객 등록(생성)에 대한 테스트")
	class create_test {

		@Nested
		@DisplayName("유효한 데이터가 입력된 경우")
		class valid_data {

			@Test
			@DisplayName("정상적으로 등록되며 요청한 데이터와 반환된 데이터가 동일하다")
			void save() {
				//Given
				Customer customer = new Customer("승원", "한");
				// When
				Customer savedCustomer = customerRepository.save(customer);

				// Then
				assertThat(customer, equalTo(savedCustomer));
			}
		}
	}

	@Nested
	@DisplayName("고객 조회에 대한 테스트")
	class read_test {

		@Nested
		@DisplayName("존재하는 고객에 대해 식별자(id)로 검색하는 경우")
		class existing_customer {

			@Test
			@DisplayName("정상적으로 데이터를 읽어 그 데이터를 반환한다 ")
			void save() {
				//Given
				Customer customer = new Customer("승원", "한");
				customerRepository.save(customer);
				long testId = customer.getId();

				// When
				Customer result = customerRepository.findById(testId).get();

				// Then
				assertThat(result, is(equalTo(customer)));
			}
		}

		@Nested
		@DisplayName("존재하지 않는 고객에 대해 식별자(id)로 검색하는 경우")
		class non_existing_customer {

			@Test
			@DisplayName("Optional.empty를 반환한다")
			void save() {
				//Given
				// When
				Optional<Customer> readCustomer = customerRepository.findById(-1L);

				// Then
				assertThat(readCustomer, is(equalTo(Optional.empty())));
			}
		}
	}

	@Nested
	@DisplayName("수정에 대한 테스트")
	class update_test {
		@Nested
		@DisplayName("수정에 성공한 경우")
		class updated {

			@Test
			@DisplayName("데이터에 수정 내역이 반영되어 있다")
			void save() {
				//Given
				Customer customer = new Customer("승원", "한");
				customerRepository.save(customer);
				long id = customer.getId();

				// When
				customer.changeFirstName("길동");
				customer.changeLastName("홍");

				// Then
				Customer updated = customerRepository.findById(id).get();
				assertThat(updated, is(new Customer(id, "길동", "홍")));
			}
		}
	}

	@Nested
	@DisplayName("삭제에 대한 테스트")
	class delete_test {
		@Nested
		@DisplayName("존재하는 고객에 대해 삭제하는 경우")
		class existing_customer {

			@Test
			@DisplayName("삭제가 데이터에 반영되어 해당 정보를 조회할 수 없다")
			void save() {
				//Given
				Customer customer = new Customer("승원", "한");
				customerRepository.save(customer);
				long id = customer.getId();

				// When
				customerRepository.deleteById(id);

				// Then
				Optional<Customer> result = customerRepository.findById(id);
				assertThat(result, is(equalTo(Optional.empty())));
			}
		}

		@Nested
		@DisplayName("존재하지 않는 고객에 대해 삭제하는 경우")
		class non_existing_customer {

			@Test
			@DisplayName("아무런 예외가 발생하지 않는다")
			void save() {
				//Given
				long testId = -1L;

				// When
				customerRepository.deleteById(testId);

				// Then
				Throwable exception = catchThrowable(() -> customerRepository.findById(testId));
				Assertions.assertThat(exception).isNull();
			}
		}
	}

}
