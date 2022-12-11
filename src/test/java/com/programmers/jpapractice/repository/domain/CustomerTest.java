package com.programmers.jpapractice.repository.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("고객 성으로 공백이나 빈 값이 들어오면 실패한다.")
	void createFailLastNameIsEmptyAndBlankTest(String firstName) {
		// given
		Customer customer = new Customer(firstName, "성준");

		// when
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("고객 이름으로 공백이나 빈 값이 들어오면 실패한다.")
	void createFailFirstNameIsEmptyAndBlankTest(String lastName) {
		// given
		Customer customer = new Customer("권", lastName);

		// when
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("고객 성이 최대 글자 초과로 입력되면 실패한다.")
	void createFailFirstNameIsOverMaxTest() {
		// given
		Customer customer = new Customer("권권권", "성준");

		// when
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("고객 이름이 최대 글자 초과로 입력되면 실패한다.")
	void createFailLastNameIsOverMaxTest() {
		// given
		Customer customer = new Customer("권", "성준성준성준");

		// when
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("고객 이름이 정상 값으로 입력되면 생성에 성공한다.")
	void createCustomerSuccessTest() {
		// given
		Customer customer = new Customer("제갈", "성준");

		// when
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

		// then
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
}