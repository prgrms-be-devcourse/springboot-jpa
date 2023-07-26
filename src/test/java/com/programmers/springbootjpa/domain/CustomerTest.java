package com.programmers.springbootjpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void of() {
        CustomerCreateRequest request = new CustomerCreateRequest(
                "Kim Jae Won",
                28,
                "hanjo",
                "서울시 마포구"
        );

        Customer customer = Customer.of(request);

        assertThat(customer.getName()).isEqualTo("Kim Jae Won");
        assertThat(customer.getAge()).isEqualTo(28);
        assertThat(customer.getNickName()).isEqualTo("hanjo");
        assertThat(customer.getAddress()).isEqualTo("서울시 마포구");
    }
}