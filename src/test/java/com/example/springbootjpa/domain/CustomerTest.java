package com.example.springbootjpa.domain;

import com.example.springbootjpa.golbal.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {

    @DisplayName("유저 생성 실패 - 잘못된 이름")
    @ParameterizedTest
    @ValueSource(strings = {"", " ",})
    @NullSource
    void invalidUsernameTest(String invalidUsername) throws Exception {

        //given -> when -> then
        assertThrows(DomainException.class,
                () -> Customer.builder()
                        .username(invalidUsername)
                        .address("부산시").build());
    }

    @DisplayName("유저 생성 실패 - 잘못된 주소")
    @ParameterizedTest
    @ValueSource(strings = {"", " ",})
    @NullSource
    void invalidAddressTest(String invalidAddress) throws Exception {

        //given -> when -> then
        assertThrows(DomainException.class,
                () -> Customer.builder()
                        .username("hong")
                        .address(invalidAddress)
                        .build());
    }
}