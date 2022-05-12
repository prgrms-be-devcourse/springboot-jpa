package com.example.jpademo.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void save() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jinuk");
        customer.setLastName("Kim");

        repository.save(customer);
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void saveEntityTest() {

        List<Customer> customers = repository.findAll();
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("읽기 테스트")
    void readEntityTest() {
        Customer entity = repository.findById(1L).get();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getFirstName()).isEqualTo("jinuk");
        assertThat(entity.getLastName()).isEqualTo("Kim");
    }

    @Test
    @DisplayName("갱신 테스트")
    void updateEntityTest() {
        Customer entity = repository.findById(1L).get();
        entity.setFirstName("UK");
        repository.save(entity);
        Customer entity2 = repository.findById(1L).get();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getFirstName()).isEqualTo("UK");
        assertThat(entity.getLastName()).isEqualTo("Kim");
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteEntityTest() {
        Customer entity = repository.findById(1L).get();
        repository.delete(entity);
        List<Customer> customers = repository.findAll();
        assertThat(customers.size()).isEqualTo(0);
    }
}