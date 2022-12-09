package com.example.mission1.repository;

import com.example.mission1.domain.Customer;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest()
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    Customer newCustomer;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void create() {
        newCustomer = Customer.builder()
                .uuid(uuid)
                .name("장영지")
                .email("youngji804@naver.com")
                .address("안양시 동안구")
                .build();

        customerRepository.save(newCustomer);
    }

    @Test
    void read() {
        var findCustomer = customerRepository.findById(uuid);

        assertThat(findCustomer.isPresent()).isTrue();
        assertThat(findCustomer.get().getEmail()).isEqualTo(newCustomer.getEmail());

        var customers = customerRepository.findAll();

        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    void update() {
        var findCustomer = customerRepository.findById(uuid).get();
        findCustomer.setAddress("이사감~");

        var reFindCustomer = customerRepository.findById(uuid).get();
        assertThat(findCustomer.getAddress()).isEqualTo(reFindCustomer.getAddress());
    }

    @Test
    void delete() {
        var findCustomer = customerRepository.findById(uuid).get();
        customerRepository.delete(findCustomer);

        var reFindCustomer = customerRepository.findById(uuid);
        assertThat(reFindCustomer.isEmpty()).isTrue();
    }

}