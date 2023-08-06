package com.programmers.week.customer.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomerTest {

    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박", "상민|박|명한|유", "건희|원|범준|고"}, delimiter = '|')
    void update_Customer_Name_Success(String firstName, String lastName, String newFirstName, String newLastName) {
        Customer customer = new Customer(firstName, lastName);
        customer.changeName(newFirstName, newLastName);

        assertAll(
                () -> assertThat(customer.getFirstName()).isEqualTo(newFirstName),
                () -> assertThat(customer.getLastName()).isEqualTo(newLastName)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박박박", "상민|박|명명명한한한|유", "건희|원|범준범준범준|고고고"}, delimiter = '|')
    void update_Customer_Name_Fail(String firstName, String lastName, String newFirstName, String newLastName) {
        Customer customer = new Customer(firstName, lastName);

        assertThatThrownBy(() -> customer.changeName(newFirstName, newLastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
