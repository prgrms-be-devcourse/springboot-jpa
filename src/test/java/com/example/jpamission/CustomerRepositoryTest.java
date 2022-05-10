package com.example.jpamission;

import com.example.jpamission.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void insert_test() {
        Customer customer = customer("yerim", "lee");

        Customer save = repository.save(customer);
        log.info("{} {} {}", save.getId(), save.getFirstName(), save.getLastName());
    }

    @Test
    void select_test() {
       Customer customer = customer("yerim", "lee");

        Customer save = repository.save(customer);

        Optional<Customer> findById = repository.findById(save.getId());

        assertThat(findById).isNotEmpty();
        assertThat(findById.get().getId()).isEqualTo(save.getId());
        log.info("{} {} {}", findById.get().getId(), findById.get().getFirstName(), findById.get().getLastName());
    }

    @Test
    void update_test() {
        Customer customer = customer("yerim", "lee");

        Customer save = repository.save(customer);

        save.setFirstName("change");
        save.setLastName("name");

        assertThat(save.getFirstName()).isEqualTo("change");
        log.info("{} {} {}", save.getId(), save.getFirstName(), save.getLastName());
    }

    @Test
    void delete_test() {
        Customer customer = customer("yerim", "lee");
        repository.save(customer);
        repository.delete(customer);

        assertThat(repository.findAll().isEmpty()).isTrue();
    }

    private Customer customer(String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

}
