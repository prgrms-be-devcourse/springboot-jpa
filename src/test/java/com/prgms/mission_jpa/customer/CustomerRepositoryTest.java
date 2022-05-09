package com.prgms.mission_jpa.customer;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    private List<Customer> customers = new ArrayList<>();

    @BeforeEach
    void setup(){
        for (long i = 0; i < 10; i++) {
            customers.add(new Customer(i, "first_" + i, "last_" + i));
        }
    }

    @AfterEach
    void cleanup(){
        customerRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"a,a","b,b","c,c"})
    void 고객을_저장할_수_있다(String firstName,String lastName) {
        Customer customer = new Customer(1L, firstName, lastName);

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 고객정보를_수정할_수_있다() {
        Customer customer = new Customer(1L, "firstName", "lastName");
        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setFirstName("yongsu");
        savedCustomer.setLastName("kang");

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("yongsu");
        assertThat(updatedCustomer.getLastName()).isEqualTo("kang");
    }

    @Test
    void 고객을_삭제할_수_있다(){
        Customer customer = new Customer(1L, "firstName", "lastName");
        customerRepository.save(customer);

        customerRepository.deleteById(customer.getId());
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.isEmpty()).isTrue();
    }

    @Test
    void 전체고객을_삭제할_수_있다(){
        customerRepository.saveAll(customers);
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size()).isEqualTo(10);

        customerRepository.deleteAll();

        List<Customer> afterDeleteCustomers = customerRepository.findAll();
        assertThat(afterDeleteCustomers.isEmpty()).isTrue();
    }
}