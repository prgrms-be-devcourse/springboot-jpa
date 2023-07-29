package com.programmers.springbootjpa.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @DisplayName("고객을 생성한다")
    @CsvSource(value = {
            "hyemin : Kim",
            "혜민 : 김"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(String firstName, String lastName) {
        //given
        //when
        Customer customer = new Customer(firstName, lastName);

        //then
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getLastName()).isEqualTo(lastName);
    }

    @DisplayName("고객을 수정한다")
    @Test
    void testUpdate() {
        //given
        Customer customer = new Customer("hyemin", "Kim");

        //when
        customer.updateFirstName("min");
        customer.updateLastName("Lee");

        //then
        assertThat(customer.getFirstName()).isEqualTo("min");
        assertThat(customer.getLastName()).isEqualTo("Lee");
    }

    @DisplayName("고객 생성 시 이름이 비어있는 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void testNameEmpty(String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Customer(name, "lastName"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 생성 시 이름이 20자를 초과할 경우 예외처리한다")
    @CsvSource(value = {
            "hyemin : aaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaaa : kim",
            "혜민 : 김김김김김김김김김김김김김김김김김김김김김김",
            "민민민민민민민민민민민민민민민민민민민민민민 : 김"
    }, delimiter = ':')
    @ParameterizedTest
    void testNameLengthOver(String firstName, String lastName) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Customer(firstName, lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 생성 시 이름에 한글 영어 이외의 문자가 있는 경우 예외처리한다")
    @CsvSource(value = {
            "혜민1 : 김",
            "@@ : 김",
            "hy3min : Kim",
            "/bbb : --"
    }, delimiter = ':')
    @ParameterizedTest
    void testNamePattern(String firstName, String lastName) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Customer(firstName, lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 수정 시 이름이 비어있는 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void testNameEmptyUpdate(String name) {
        //given
        Customer customer = new Customer("hyemin", "Kim");

        //when
        //then
        assertThatThrownBy(() -> customer.updateLastName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 수정 시 이름이 20자를 초과할 경우 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "김김김김김김김김김김김김김김김김김김김김김김"})
    @ParameterizedTest
    void testNameLengthOverUpdate(String lastName) {
        //given
        Customer customer = new Customer("hyemin", "Kim");

        //when
        //then
        assertThatThrownBy(() -> customer.updateLastName(lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("고객 수정 시 이름에 한글과 영어 이외의 문자가 있는 경우 예외처리한다")
    @ValueSource(strings = {"11", "#", "hh3", "민111", "min!"})
    @ParameterizedTest
    void testNamePatternUpdate(String firstName) {
        //given
        Customer customer = new Customer("hyemin", "Kim");

        //when
        //then
        assertThatThrownBy(() -> customer.updateFirstName(firstName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}