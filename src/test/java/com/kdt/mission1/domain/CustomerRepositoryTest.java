package com.kdt.mission1.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
@Rollback(value = false)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;


    @Test
    @DisplayName("객체를 저장할 수 있다")
    public void testSave() {
        // Given
        Customer customer = new Customer("seongwon", "choi");
        // When
        Customer saved = repository.save(customer);
        // Then
        Customer persistCustomer = repository.findById(saved.getId()).get();
        assertThat(customer, samePropertyValuesAs(persistCustomer));

    }

    @Test
    @DisplayName("저장된 객체를 수정해 다시 저장할 수 있다")
    void testUpdate() {
        // Given
        Customer customer = new Customer("seongwon", "choi");
        repository.save(customer);
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