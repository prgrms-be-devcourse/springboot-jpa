package com.part4.jpa2;

import com.part4.jpa2.config.DataSourceConfig;
import com.part4.jpa2.domain.Customer;
import com.part4.jpa2.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer;
    Customer savedCustomer;

    @BeforeEach
    void 고객_CREATE(){
        customer = Customer.builder()
                .firstName("subin")
                .lastName("kim").build();
        savedCustomer = customerRepository.save(customer);
    }

    @Test
    void 고객_READ(){
        var entity = customerRepository.findById(savedCustomer.getId()).get();
        assertThat(entity).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 고객_UPDATE_AND_READ(){
        var entity = customerRepository.findById(savedCustomer.getId()).get();

        entity.setFirstName("SUBIN");
        entity.setLastName("KIM");

        var entity2 = customerRepository.findById(savedCustomer.getId()).get();
        assertThat(entity).usingRecursiveComparison().isEqualTo(entity2);
    }

    @Test
    void 고객_DELETE_AND_READ(){
        assertThat(customerRepository.count()).isEqualTo(1);
        customerRepository.deleteById(savedCustomer.getId());

        var allCustomers = customerRepository.findAll();
        assertThat(customerRepository.count()).isEqualTo(0);
        assertThat(allCustomers).doesNotContain(customer);
    }
}
