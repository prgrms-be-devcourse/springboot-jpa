package com.programmers.jpa.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void nameFailTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new Customer(1L, "", "");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new Customer(2L, "lee", "");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new Customer(3L, "", "hanju");
                })
        );
    }

}