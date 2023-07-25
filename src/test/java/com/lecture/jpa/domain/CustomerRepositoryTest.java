package com.lecture.jpa.domain;

import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void 고객정보가_저장되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("beomu");
        customer.setLastName("kim");

        // When
        customerRepository.save(customer);

        // Then
        Customer selectedEntity = customerRepository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 고객정보가_수정되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("beomu");
        customer.setLastName("kim");
        Customer entity = customerRepository.save(customer);

        // When
        entity.setFirstName("heungmin");
        entity.setLastName("son");

        // Then
        Customer selectedEntity = customerRepository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(selectedEntity.getLastName()).isEqualTo(entity.getLastName());
    }

    @Test
    void 단건조회를_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("beomu");
        customer.setLastName("kim");
        customerRepository.save(customer);

        // When
        Customer selected = customerRepository.findById(customer.getId()).get();

        // Then
        assertThat(customer.getId()).isEqualTo(selected.getId());
    }

    @Test
    void 리스트조회를_확인한다() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("beomu");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("heungmin");
        customer2.setLastName("son");



        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        // When
        List<Customer> selectedCustomers = customerRepository.findAll();

        // Then
        assertThat(selectedCustomers.size()).isEqualTo(2);
    }
}
