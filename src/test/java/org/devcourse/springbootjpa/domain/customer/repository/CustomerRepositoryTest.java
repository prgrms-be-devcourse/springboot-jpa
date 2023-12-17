package org.devcourse.springbootjpa.domain.customer.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.devcourse.springbootjpa.domain.customer.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    private Customer setUpCustomer;

    @BeforeEach
    void setUp() {
        this.setUpCustomer = Customer.createWithNames("insoo", "cho");
        customerRepository.save(setUpCustomer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객정보가 저장되는지 확인한다")
    void saveCustomerToDB() {
        // Given
        Customer customer = Customer.createWithNames("insoo", "cho");

        // When
        customerRepository.save(customer);

        // Then
        Customer selectedEntity = customerRepository.findById(customer.getId()).get();
        assertThat(selectedEntity.getId()).isEqualTo(customer.getId());
        assertThat(selectedEntity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @DisplayName("고객정보가 수정되는지 확인한다")
    void updateCustomerInDB() {
        // When
        setUpCustomer.setFirstName("guppy");
        setUpCustomer.setLastName("hong");

        // Then
        Customer selectedEntity = customerRepository.findById(setUpCustomer.getId()).get();
        assertThat(selectedEntity.getId()).isEqualTo(setUpCustomer.getId());
        assertThat(selectedEntity.getFirstName()).isEqualTo("guppy");
        assertThat(selectedEntity.getLastName()).isEqualTo("hong");
    }

    @Test
    @DisplayName("단건조회를 확인한다")
    void findACustomerByIdInDB() {
        // Given
        // When
        Customer selected = customerRepository.findById(setUpCustomer.getId()).get();

        // Then
        assertThat(selected.getId()).isEqualTo(setUpCustomer.getId());
        assertThat(selected.getFirstName()).isEqualTo(setUpCustomer.getFirstName());
    }

    @Test
    @DisplayName("리스트 조회를 확인한다")
    void findAllCustomersInDB() {
        // Given
        Customer customer1 = Customer.createWithNames("insoo1", "cho");
        Customer customer2 = Customer.createWithNames("insoo2", "cho");

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        // When
        List<Customer> selectedCustomers = customerRepository.findAll();

        // Then
        assertThat(selectedCustomers.size()).isEqualTo(3);
    }
}
