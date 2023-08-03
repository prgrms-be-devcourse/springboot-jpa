package org.programmers.jpaweeklymission.customer;

import org.assertj.core.api.RecursiveComparisonAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {
    private Customer customer;

    @BeforeEach
    void setup() {
        customer = Customer.builder()
                .firstName("길동")
                .lastName("홍")
                .build();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    void testChangeEntity() {
        // given
        Customer forUpdate = Customer.builder()
                .firstName("상민")
                .lastName("박")
                .build();
        // when
        customer.changeEntity(forUpdate);
        // then
        assertThat(customer).usingRecursiveComparison().isEqualTo(forUpdate);
    }
}
