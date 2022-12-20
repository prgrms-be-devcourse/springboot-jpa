package com.programmers.kwonjoosung.springbootjpa.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class CustomerTest {

    private static final String firstName = "joosung";
    private static final String lastName = "kwon";
    private Customer customer = new Customer(firstName, lastName);


    @Test
    @DisplayName("[성공] Customer를 생성할 수 있다.")
    void createTest() {
        //given & when
        Customer newCustomer = new Customer(firstName, lastName);
        //then
        assertThat(newCustomer)
                .hasFieldOrPropertyWithValue("firstName", firstName)
                .hasFieldOrPropertyWithValue("lastName", lastName);
    }

    @Test
    @DisplayName("[성공] Customer의 필드를 수정할 수 있다")
    void updateTest() {
        //when
        customer.changeFirstName("SUNGJOO");
        customer.changeLastName("KIM");
        //then
        assertThat(customer)
                .hasFieldOrPropertyWithValue("firstName", "SUNGJOO")
                .hasFieldOrPropertyWithValue("lastName", "KIM");
    }

    @Test
    @DisplayName("[실패] 이름에는 숫자가 들어갈 수 없다")
    void nameValidationTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName("SUNGJOO1")),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName("KIM1"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ",""})
    @DisplayName("[실패] 이름에는 공백, space 이 입력 될 수 없다")
    void nameValidationTest2(String value) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName(value)),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName(value))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"joosungjoosungjoosungjoosung"})
    @DisplayName("[실패] 이름은 20자를 넘을 수 없다")
    void nameValidationTest3(String value) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName(value)),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName(value))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ","","joosungjoosungjoosungjoosung"})
    @DisplayName("[실패] 이름 규칙에 맞지 않으면 생성할 수 없다")
    void nameValidationTest4(String value) {
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Customer(null, lastName)), // @NotBlank가 동작하는 것인지? -> valid는 다른 방식으로 검증 필요함
                () -> assertThrows(NullPointerException.class, () -> new Customer(firstName, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, value)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(value, lastName))
        );
    }

}