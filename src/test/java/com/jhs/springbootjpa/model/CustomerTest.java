package com.jhs.springbootjpa.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    @DisplayName("고객 생성에 성공한다")
    void successCreateCustomer() {
        Customer customer = new Customer(1L, "hongseok", "Ju");

        assertThat(customer).isNotNull();
    }

    @ParameterizedTest(name = "{index} firstName : {0} & lastName : {1}")
    @MethodSource("customerNames")
    @DisplayName("이름 검증에 실패하여 생성에 실패한다")
    void failCreateCustomer(String firstName, String lastName) {
        assertThatThrownBy(() -> new Customer(1L, firstName, lastName))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> customerNames() {
        return Stream.of(
                Arguments.arguments("", ""),
                Arguments.arguments("", null),
                Arguments.arguments(null, ""),
                Arguments.arguments(null, null),
                Arguments.arguments("", "Ju"),
                Arguments.arguments("hongseok", ""),
                Arguments.arguments("hongseok", " "),
                Arguments.arguments(" ", "Ju")
        );
    }
}
