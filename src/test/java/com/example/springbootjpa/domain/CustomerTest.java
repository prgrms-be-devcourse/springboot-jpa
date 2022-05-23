package com.example.springbootjpa.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.example.springbootjpa.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterAll
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    void 생성() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("suy2on");
        customer.setAge(25);

        customerRepository.save(customer);

        assertThat(customerRepository.existsById(1L), is(true));

    }

    @Test
    @Order(2)
    void 조회() {
        List<Customer> customerList = customerRepository.findAll();
        for (Customer customer : customerList) {
            log.info("{} : {}세", customer.getName(), customer.getAge());
        }

    }

    @Test
    @Order(3)
    void 수정() {
        Optional<Customer> customer = customerRepository.findById(1L);
        customer.get().setName("changeName");
        customerRepository.save(customer.get());

        Optional<Customer> updated = customerRepository.findById(1L);

        assertThat(customer.get(), samePropertyValuesAs(updated.get()));

    }

    @Test
    @Order(4)
    void 삭제() {
        Optional<Customer> customer = customerRepository.findById(1L);
        customerRepository.deleteById(1L);

        assertThat(customerRepository.existsById(1L), is(false));

    }


}
