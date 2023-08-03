package com.programmers.springbootjpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("Customer 생성 Dto에서 Customer Entity로 정상적으로 변환되는지 테스트한다.")
    void customerDtoToEntityTest() {
        CustomerCreateRequest request = new CustomerCreateRequest(
                "Kim Jae Won",
                28,
                "hanjo",
                "서울시 마포구"
        );

        Customer actualEntity = request.toEntity();

        Customer expectedEntity = Customer.builder()
                .name("Kim Jae Won")
                .age(28)
                .nickName("hanjo")
                .address("서울시 마포구")
                .build();

        assertThat(actualEntity).usingRecursiveComparison().isEqualTo(expectedEntity);
    }
}
