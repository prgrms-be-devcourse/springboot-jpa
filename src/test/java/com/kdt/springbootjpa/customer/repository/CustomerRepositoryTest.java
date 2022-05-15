package com.kdt.springbootjpa.customer.repository;

import com.kdt.springbootjpa.common.entity.AccountAuditAware;
import com.kdt.springbootjpa.customer.domain.Customer;
import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = ASSIGNABLE_TYPE,
        classes = {AccountAuditAware.class}
))
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRepositoryTest(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    void save_test() {
        //given
        final var customer = new Customer();
        customer.setId(1L);
        customer.setName("san", "kim");

        //when
        final var savedCustomer = customerRepository.save(customer);

        //then
        assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    @Order(2)
    void find_test() {
        //given
        final var customer = new Customer();
        customer.setId(1L);
        customer.setName("san", "kim");
        final var savedCustomer = customerRepository.save(customer);

        //when
        final var foundCustomer = customerRepository.findById(savedCustomer.getId());

        // then
        assertTrue(foundCustomer.isPresent());
        assertThat(foundCustomer.get()).isEqualTo(savedCustomer);
    }

    @Test
    @Order(3)
    void update_test() {
        //given
        final var customer = new Customer();
        customer.setId(1L);
        customer.setName("san", "kim");
        final var savedCustomer = customerRepository.save(customer);

        //when
        savedCustomer.setName("river", "kim");

        //then
        final var foundCustomer = customerRepository.findById(savedCustomer.getId());
        assertTrue(foundCustomer.isPresent());
        assertThat(foundCustomer.get()).isEqualTo(savedCustomer);
    }

    @Test
    @Order(4)
    void delete_test() {
        //given
        final var customer = new Customer();
        customer.setId(1L);
        customer.setName("san", "kim");
        final var savedCustomer = customerRepository.save(customer);

        //when
        customerRepository.deleteById(savedCustomer.getId());

        //then
        final var foundCustomer = customerRepository.findById(savedCustomer.getId());
        assertTrue(foundCustomer.isEmpty());
    }

    @Test
    @Order(5)
    void base_entity_test() {
        //given
        final var customer = new Customer();
        customer.setId(1L);
        customer.setName("san", "kim");

        //when
        final var savedCustomer = customerRepository.save(customer);

        //then
        assertAll(
                () -> assertNotNull(savedCustomer.getCreatedBy()),
                () -> assertNotNull(savedCustomer.getCreatedAt()),
                () -> assertNotNull(savedCustomer.getUpdatedAt())
        );

        log.info(savedCustomer.getCreatedBy());
    }
}
