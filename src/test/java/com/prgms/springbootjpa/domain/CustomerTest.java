package com.prgms.springbootjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();
    private static final String FIRST_NAME = "Tina";
    private static final String LAST_NAME = "Jeong";
    private static final String EMPTY_STRING = "";
    private static final int MIN = 1;
    private static final int MAX = 50;
    private static final String OVER_SIZE_STRING = String.join("", Collections.nCopies(MAX + 1, "a"));
    private final Logger logger = LoggerFactory.getLogger(CustomerTest.class);

    @Test
    @DisplayName("Customer 생성에 성공한다.")
    void 소비자를생성한다() {
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
        assertEquals(0, validations.size());
    }

    @Test
    @DisplayName("Customer의 firstName에는 공백이 들어갈 수 없다.")
    void 이름에공백을넣는다() {
        Customer customer = new Customer(EMPTY_STRING, LAST_NAME);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("이름 '%s' 의 길이는 %d이상 %d이하여야 합니다.", EMPTY_STRING, MIN, MAX))).count());
    }

    @Test
    @DisplayName("Customer의 lastName에는 공백이 들어갈 수 없다.")
    void 성에공백을넣는다() {
        Customer customer = new Customer(FIRST_NAME, EMPTY_STRING);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("성 '%s' 의 길이는 %d이상 %d이하여야 합니다.", EMPTY_STRING, MIN, MAX))).count());
    }

    @Test
    @DisplayName("Customer의 firstName에는 null이 들어갈 수 없다.")
    void 이름에Null을넣는다() {
        Customer customer = new Customer(null, LAST_NAME);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals("널이어서는 안됩니다")).count());
    }

    @Test
    @DisplayName("Customer의 lastName에는 null이 들어갈 수 없다.")
    void 성에Null을넣는다() {
        Customer customer = new Customer(FIRST_NAME, null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals("널이어서는 안됩니다")).count());
    }


    @Test
    @DisplayName("Customer의 firstName은 최대길이를 초과할 수 없다.")
    void 이름에최대길이를초과해넣는다() {
        Customer customer = new Customer(OVER_SIZE_STRING, LAST_NAME);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("이름 '%s' 의 길이는 %d이상 %d이하여야 합니다.", OVER_SIZE_STRING, MIN, MAX))).count());
    }


    @Test
    @DisplayName("Customer의 lastName은 최대길이를 초과할 수 없다.")
    void 성에최대길이를초과해넣는다() {
        Customer customer = new Customer(FIRST_NAME, OVER_SIZE_STRING);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("성 '%s' 의 길이는 %d이상 %d이하여야 합니다.", OVER_SIZE_STRING, MIN, MAX))).count());
    }

    @Test
    @DisplayName("Customer의 firstName을 수정하는데 성공한다.")
    void 이름을수정한다() {
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        String koreanFirstName = "티나";
        customer.modifyFirstName(koreanFirstName);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertEquals(koreanFirstName, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
        assertEquals(0, validations.size());
    }

    @Test
    @DisplayName("Customer의 name을 수정할 때 공백이 들어갈 수 없다.")
    void 이름을수정할때공백을넣는다() {
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        customer.modifyFirstName(EMPTY_STRING);
        customer.modifyLastName(EMPTY_STRING);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);
        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("이름 '%s' 의 길이는 %d이상 %d이하여야 합니다.", EMPTY_STRING, MIN, MAX))).count());
        assertEquals(1, validations.stream().filter(message -> message.equals(String.format("성 '%s' 의 길이는 %d이상 %d이하여야 합니다.", EMPTY_STRING, MIN, MAX))).count());
    }

    @Test
    @DisplayName("Customer의 name을 수정할 때 null이 들어갈 수 없다.")
    void 이름을수정할때null을넣는다() {
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        customer.modifyFirstName(null);
        customer.modifyLastName(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> validations = violations.stream().map(ConstraintViolation::getMessage).toList();
        validations.forEach(logger::info);

        assertEquals(2, validations.stream().filter(message -> message.equals("널이어서는 안됩니다")).count());
    }
}