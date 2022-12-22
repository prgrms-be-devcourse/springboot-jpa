package com.example.springbootjpa.customer.repository;

import com.example.springbootjpa.BaseTestContainer;
import com.example.springbootjpa.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest extends BaseTestContainer {
    @Test
    @DisplayName("엔티티 저장 테스트")
    void saveTest () {
        // Given
        Customer customer = new Customer();

        // When

        // Then
    }
}