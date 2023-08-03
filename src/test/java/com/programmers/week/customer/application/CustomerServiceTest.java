package com.programmers.week.customer.application;

import com.programmers.week.customer.presentation.CustomerCreateRequest;
import com.programmers.week.customer.presentation.CustomerResponse;
import com.programmers.week.customer.presentation.CustomerUpdateRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void create_Customer_Success(String firstName, String lastName) {
        CustomerCreateRequest request = new CustomerCreateRequest(firstName, lastName);

        Long id = customerService.create(request);
        CustomerResponse response = customerService.findById(id);

        assertEquals(firstName, response.firstName());
        assertEquals(lastName, response.lastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박", "상민|박|명한|유", "건희|원|범준|고"}, delimiter = '|')
    void update_Customer_Name_Success(String firstName, String lastName, String newFirstName, String newLastName) {
        CustomerCreateRequest request = new CustomerCreateRequest(firstName, lastName);
        Long id = customerService.create(request);

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newFirstName, newLastName);
        customerService.update(id, updateRequest);
        CustomerResponse customer = customerService.findById(id);

        assertEquals(newFirstName, customer.firstName());
        assertEquals(newLastName, customer.lastName());
    }

    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void find_Customer_Fail(String firstName, String lastName) {
        CustomerCreateRequest request = new CustomerCreateRequest(firstName, lastName);
        Long id = customerService.create(request);
        customerService.deleteById(id);

        assertThatThrownBy(() -> customerService.findById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
