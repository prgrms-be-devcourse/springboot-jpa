package com.programmers.springbootjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @DisplayName("Customer를 생성한다")
    @CsvSource(value = {
            "hyemin : Kim",
            "혜민 : 김"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(String firstName, String lastName) {
        //given
        //when
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        //then
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getLastName()).isEqualTo(lastName);
    }

    @DisplayName("Customer를 수정한다")
    @Test
    void testUpdate() {
        //given
        Customer customer = Customer.builder()
                .firstName("hyemin")
                .lastName("Kim")
                .build();

        //when
        customer.updateFirstName("min");
        customer.updateLastName("Lee");

        //then
        assertThat(customer.getFirstName()).isEqualTo("min");
        assertThat(customer.getLastName()).isEqualTo("Lee");
    }

    @DisplayName("Customer 생성 시 이름이 비어있는 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void testNameEmpty(String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> Customer.builder()
                .firstName(name)
                .lastName(name)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Customer 생성 시 이름이 20자를 초과할 경우 예외처리한다")
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
        assertThatThrownBy(() -> Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Customer 생성 시 이름에 한글 영어 이외의 문자가 있는 경우 예외처리한다")
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
        assertThatThrownBy(() -> Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Customer 수정 시 이름이 비어있는 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void testNameEmptyUpdate(String name) {
        //given
        Customer customer = Customer.builder()
                .firstName("hyemin")
                .lastName("Kim")
                .build();

        //when
        //then
        assertThatThrownBy(() -> customer.updateLastName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Customer 수정 시 이름이 20자를 초과할 경우 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "김김김김김김김김김김김김김김김김김김김김김김"})
    @ParameterizedTest
    void testNameLengthOverUpdate(String lastName) {
        //given
        Customer customer = Customer.builder()
                .firstName("hyemin")
                .lastName("Kim")
                .build();

        //when
        //then
        assertThatThrownBy(() -> customer.updateLastName(lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Customer 수정 시 이름에 한글과 영어 이외의 문자가 있는 경우 예외처리한다")
    @ValueSource(strings = {"11", "#", "hh3", "민111", "min!"})
    @ParameterizedTest
    void testNamePatternUpdate(String firstName) {
        //given
        Customer customer = Customer.builder()
                .firstName("hyemin")
                .lastName("Kim")
                .build();

        //when
        //then
        assertThatThrownBy(() -> customer.updateFirstName(firstName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}