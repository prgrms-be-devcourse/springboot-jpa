package com.programmers.week.customer.domain;

import com.programmers.week.customer.infra.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerRepository customerRepository;

    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void create_Customer_Success(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        Customer savedCustomer = customerRepository.save(customer);
        assertEquals(firstName, savedCustomer.getFirstName());
        assertEquals(lastName, savedCustomer.getLastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"은지은지은지은지은지|박", "명한|유유유유유유유", "범준범준범준범준범준|고고고고고고고고"}, delimiter = '|')
    @DisplayName("성과 이름의 길이가 올바르지 않을 경우 고객을 생성할 수 없다.")
    void create_Customer_Fail(String firstName, String lastName) {
        assertThatThrownBy(() -> new Customer(firstName, lastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박", "상민|박|명한|유", "건희|원|범준|고"}, delimiter = '|')
    void update_Customer_Name_Success(String firstName, String lastName, String newFirstName, String newLastName) {
        Customer customer = new Customer(firstName, lastName);
        customer.changeName(newFirstName, newLastName);
        assertEquals(newFirstName, customer.getFirstName());
        assertEquals(newLastName, customer.getLastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박박박", "상민|박|명명명한한한|유", "건희|원|범준범준범준|고고고"}, delimiter = '|')
    void update_Customer_Name_Fail(String firstName, String lastName, String newFirstName, String newLastName) {
        Customer customer = new Customer(firstName, lastName);

        assertThatThrownBy(() -> customer.changeName(newFirstName, newLastName))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
