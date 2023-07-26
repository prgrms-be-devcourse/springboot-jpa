package org.prgms.springbootjpa.mission1.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission1.customer.domain.CustomerEntity;
import org.prgms.springbootjpa.mission1.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    CustomerEntity insertCustomer = new CustomerEntity(1L, "hyeonji", "park");

    @Test
    @DisplayName("customer를 추가할 수 있다.")
    void save() {
        CustomerEntity customer = customerRepository.save(insertCustomer);

        assertThat(insertCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("id로 customer를 조회할 수 있다.")
    void findById() {
        CustomerEntity save = customerRepository.save(insertCustomer);

        Optional<CustomerEntity> customer = customerRepository.findById(insertCustomer.getId());

        assertThat(customer.isEmpty(), is(false));
        assertThat(save, samePropertyValuesAs(customer.get()));
    }

    @Test
    @Transactional
    @DisplayName("customer를 수정할 수 있다.")
    void update() {
        CustomerEntity customer = customerRepository.save(insertCustomer);

        customer.changeFirstName("hyeonz");

        Optional<CustomerEntity> updated = customerRepository.findById(insertCustomer.getId());

        assertThat(updated.isEmpty(), is(false));
        assertThat(customer, samePropertyValuesAs(updated.get()));
    }

    @Test
    @DisplayName("모든 customer를 조회할 수 있다.")
    void findAll() {
        customerRepository.save(insertCustomer);
        customerRepository.save(new CustomerEntity(2L, "hyeonji", "kim"));

        List<CustomerEntity> customers = customerRepository.findAll();

        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("id로 customer를 삭제할 수 있다.")
    void deleteById() {
        customerRepository.save(insertCustomer);

        customerRepository.deleteById(insertCustomer.getId());

        List<CustomerEntity> customers = customerRepository.findAll();

        assertThat(customers, empty());
    }
}
