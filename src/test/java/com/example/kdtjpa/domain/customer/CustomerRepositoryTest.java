package com.example.kdtjpa.domain.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @Test
    void saveTest() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");

        // When
        repository.save(customer);

        // Then
        List<Customer> customers = repository.findAll();
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    void findTest() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");
        repository.save(customer);

        // When
        Customer foundCustomer = repository.findById(customer.getId()).get();

        // Then
        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    void updateTest() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");
        Customer entity = repository.save(customer);

        // When
        entity.setFirstName("hayoung2");

        // Then
        Customer updatedEntity = repository.findById(entity.getId()).get();
        assertThat(updatedEntity.getFirstName()).isEqualTo(entity.getFirstName());
    }

    @Test
    void deleteTest() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");
        repository.save(customer);

        // When
        repository.delete(customer);

        // Then
        assertThat(repository.findById(customer.getId())).isEmpty();
    }
}
