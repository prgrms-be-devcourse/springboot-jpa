package com.example.mission1.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CustomerTest {

    @Test
    @DisplayName("customer 필드 validation")
    void createFailTest() {
        Customer customer = new Customer(null, null, null, null);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Customer>> validate = validator.validate(customer);
        validate.forEach(v -> log.info("{}", v));
        assertThat(validate.size()).isEqualTo(4);
    }

}