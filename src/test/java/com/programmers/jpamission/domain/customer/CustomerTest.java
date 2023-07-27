package com.programmers.jpamission.domain.customer;

import com.programmers.jpamission.global.exception.ErrorMessage;
import com.programmers.jpamission.global.exception.InvalidNameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class CustomerTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("Customer Repository 가 Bean 으로 등록된다")
    void customer_repository_bean(){
        assertThat(repository).isNotNull();
    }

    @DisplayName("고객 등록 - 성공")
    @Test
    void customer_create() {
        // given
        Customer customer1 = new Customer("재윤", "신");
        Customer customer2 = new Customer("길동", "홍");

        // when
        repository.save(customer1);
        repository.save(customer2);

        // then
        List<Customer> all = repository.findAll();
        assertThat(all).hasSize(2)
                .contains(customer1, customer2);
    }

    @DisplayName("고객 등록 실패 - 이름 길이")
    @ParameterizedTest
    @CsvSource({
            ", 신", "1234567891011121314151617181920, 신"
    })
    void customer_create_fail_firstName(String firstName, String lastName) {
        // then
        assertThatThrownBy(() -> new Customer(firstName, lastName))
                .isInstanceOf(InvalidNameRequest.class)
                .hasMessage(ErrorMessage.INVALID_FIRST_NAME_REQUEST.getMessage());
    }

    @DisplayName("고객 등록 실패 - 성 길이")
    @ParameterizedTest
    @CsvSource({
            "재윤, ", "재윤, 123456789000"
    })
    void customer_create_fail_lastName(String firstName, String lastName) {
        // then
        assertThatThrownBy(() -> new Customer(firstName, lastName))
                .isInstanceOf(InvalidNameRequest.class)
                .hasMessage(ErrorMessage.INVALID_LAST_NAME_REQUEST.getMessage());
    }

    @DisplayName("고객 이름 수정 - 성공")
    @Test
    void customer_name_update() {
        // given
        Customer customer = new Customer("재윤", "신");
        repository.save(customer);
        Name name = Name.of("길동", "홍");

        // when
        customer.updateCustomerName(name);

        // then
        List<Customer> result = repository.findAll();
        assertThat(result.get(0).getName()).isEqualTo(name);
    }

    @DisplayName("고객 삭제 - 성공")
    @Test
    void customer_delete() {
        // given
        Customer customer1 = new Customer("재윤", "신");
        Customer customer2 = new Customer("길동", "홍");
        repository.save(customer1);
        repository.save(customer2);

        // when
        repository.delete(customer1);

        // then
        List<Customer> all = repository.findAll();
        assertThat(all).hasSize(1)
                .contains(customer2);
    }
}
