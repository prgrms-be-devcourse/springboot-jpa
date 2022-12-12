package com.prgrms.be.jpa.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class CustomerTest {

    private static final Long ID = 1L;
    private static final String FIRST = "수영";
    private static final String LAST = "이";
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("id에 null을 넣을 수 없다.")
    void id_fail_test() {
        // given
        Customer customer = new Customer();
        customer.setId(null);
        customer.setFirstName(FIRST);
        customer.setLastName(LAST);

        // when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // then
        assertThat(violations.isEmpty(), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "수영성준태현"
    })
    @DisplayName("이름(firstName)을 1~5자 범위 밖으로 설정할 수 없다.")
    void firstName_fail_test(String firstName) {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(firstName);
        customer.setLastName(LAST);

        // when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // then
        assertThat(violations.isEmpty(), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "수영이"
    })
    @DisplayName("성(lastName)을 1~2자 범위 밖으로 설정할 수 없다.")
    void lastName_fail_test(String lastName) {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST);
        customer.setLastName(lastName);

        // when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // then
        assertThat(violations.isEmpty(), is(false));
    }

    @Test
    @DisplayName("고객 정상적인 id와 범위 내의 길이를 가진 이름, 성을 설정할 수 있다.")
    void success_test() {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST);
        customer.setLastName(LAST);

        // when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // then
        assertThat(violations.isEmpty(), is(true));
    }
}