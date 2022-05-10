package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void saveTest() {
        //Given
        Customer customer = new Customer(1L, "sanghyeok", "park");

        //When
        repository.save(customer);

        //Then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(entity.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void findTest() {
        //Given
        Customer customer = new Customer(1L, "sanghyeok", "park");

        Customer customer0 = new Customer(2L, "mk", "2");

        //When
        repository.save(customer);
        repository.save(customer0);

        //Then
        List<Customer> customers = repository.findAll();
        assertThat(customers).hasSize(2);
    }

    @Test
    void updateTest() {
        //Given
        Customer customer = new Customer(1L, "sanghyeok", "park");
        repository.save(customer);

        //When
        customer.setFirstName("test");
        customer.setLastName("kim");
        repository.save(customer);

        //Then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(entity.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void deleteTest() {
        //Given
        Customer customer = new Customer(1L, "sanghyeok", "park");
        Customer customer0 = new Customer(2L, "mk", "2");
        repository.save(customer);
        repository.save(customer0);

        //When
        repository.delete(customer);

        //Then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity).isEmpty();
        List<Customer> customers = repository.findAll();
        assertThat(customers).hasSize(1);
    }

    @Test
    void deleteAllTest() {
        //Given
        Customer customer = new Customer(1L, "sanghyeok", "park");
        Customer customer0 = new Customer(2L, "mk", "2");
        repository.save(customer);
        repository.save(customer0);

        //When
        repository.deleteAll();

        //Then
        List<Customer> customers = repository.findAll();
        assertThat(customers).hasSize(0);
    }
}