package com.kdt.mission1.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

//    @BeforeEach
//    void setup() {
//        repository.deleteAll();
//    }

    @Test
    @DisplayName("객체를 저장할 수 있다")
    @Order(1)
    public void testSave() {
        // Given
        Customer customer = new Customer(1L, "seongwon", "choi");
        // When
        repository.save(customer);
        // Then
        Customer persistCustomer = repository.findById(1L).get();
        assertThat(customer, samePropertyValuesAs(persistCustomer));

    }

    @Test
    @DisplayName("저장된 객체를 수정해 다시 저장할 수 있다")
    @Order(2)
    void testUpdate() {
        // Given
        Customer customer = new Customer(2L, "seongwon", "choi");
        Customer persistCustomer = repository.findById(customer.getId()).get();
        // When
        persistCustomer.setFirstName("jerry");
        persistCustomer.setLastName("tom");
        repository.save(persistCustomer);

        // Then
        Customer newCustomer = repository.findById(persistCustomer.getId()).get();
        assertThat(newCustomer, samePropertyValuesAs(persistCustomer));
    }

    @Test
    @DisplayName("dirty check 를 통해 save 없이 객체를 수정할 수 있다")
    @Order(3)
    void testDirtyCheckingUpdate() {

        // Given
        Customer customer = new Customer("seongwon", "choi");
        Customer saved = repository.save(customer);
        Customer persistCustomer = repository.findById(saved.getId()).get();
        // When
        persistCustomer.setFirstName("jerry");

        // Then
        Customer newCustomer = repository.findById(persistCustomer.getId()).get();
        assertThat(newCustomer, samePropertyValuesAs(persistCustomer));
    }

}