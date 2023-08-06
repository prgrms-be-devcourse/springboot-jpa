package com.programmers.week.customer.application;

import com.programmers.week.customer.domain.Customer;
import com.programmers.week.customer.infra.CustomerRepository;
import com.programmers.week.customer.presentation.CustomerResponse;
import com.programmers.week.customer.presentation.CustomerUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAllInBatch();
    }

    @DisplayName("존재하는 고객 아이디와 올바른 이름을 입력할 경우 고객을 수정할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"영경|나|은지|박", "상민|박|명한|유", "건희|원|범준|고"}, delimiter = '|')
    void updateCustomerTest(String firstName, String lastName, String newFirstName, String newLastName) {
        Customer customer = new Customer(firstName, lastName);
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newFirstName, newLastName);
        customerService.update(id, updateRequest);

        Customer findCustomer = customerRepository.findById(id).get();
        assertAll(
                () -> assertThat(newFirstName).isEqualTo(findCustomer.getFirstName()),
                () -> assertThat(newLastName).isEqualTo(findCustomer.getLastName())
        );
    }

    @DisplayName("존재하는 고객 아이디를 입력할 경우 고객을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void findCustomerTest(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();

        CustomerResponse findCustomer = customerService.findById(id);

        assertAll(
                () -> assertThat(findCustomer.firstName()).isEqualTo(firstName),
                () -> assertThat(findCustomer.lastName()).isEqualTo(lastName)
        );
    }

    @DisplayName("존재하는 고객 아이디를 입력할 경우 고객을 삭제할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void deleteCustomerTest(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();

        customerService.deleteById(id);

        Optional<Customer> findUser = customerRepository.findById(id);
        assertFalse(findUser.isPresent());
    }

    @DisplayName("존재하지 않는 고객을 수정할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"영경|나", "상민|박", "건희|원"}, delimiter = '|')
    void updateCustomerFailTest(String newFirstName, String newLastName) {
        assertThatThrownBy(() -> customerService.update(1029384756L, new CustomerUpdateRequest(newFirstName, newLastName)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
