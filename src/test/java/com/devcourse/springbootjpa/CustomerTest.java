package com.devcourse.springbootjpa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.devcourse.springbootjpa.domain.Customer;
import com.devcourse.springbootjpa.exception.InvalidCustomerException;

class CustomerTest {
	@Nested
	@DisplayName("이름이")
	class FirstName {
		@DisplayName("11자 이상 : InvalidCustomerException 발생")
		@Test
		void is_too_long() {
			assertThrows(InvalidCustomerException.class,
					() -> new Customer(1L, "가나다라마바사아자차카", "한"));
		}

		@Test
		@DisplayName("공백인 경우 : InvalidCustomerException 발생")
		void is_blank() {
			assertThrows(InvalidCustomerException.class,
					() -> new Customer(1L, " ", "한"));
		}
	}

	@Nested
	@DisplayName("성이")
	class LastName {
		@DisplayName("6자 이상 : InvalidCustomerException 발생")
		@Test
		void is_too_long() {
			assertThrows(InvalidCustomerException.class,
					() -> new Customer(1L, "승원", "가나다라마바")
			);
		}

		@Test
		@DisplayName("공백인 경우 : InvalidCustomerException 발생")
		void is_blank() {
			assertThrows(InvalidCustomerException.class,
					() -> new Customer(1L, "승원", " ")
			);
		}
	}
}
