package com.example.springbootpart4.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void 고객정보가_저장되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");

        // When
        repository.save(customer);

        // Then
        Customer selectedEntity = repository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 고객정보가_단건조회되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        Customer selectedEntity = repository.findById(customer.getId()).get();

        // Then
        assertThat(selectedEntity.getId()).isEqualTo(customer.getId());
    }

    @Test
    void 고객정보가_전체조회되는지_확인한다() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("sohyeon");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("hyeon");
        customer2.setLastName("so");

        repository.save(customer1);
        repository.save(customer2);

        // When
        List<Customer> selectedEntities = repository.findAll();

        // Then
        assertThat(selectedEntities.size()).isEqualTo(2);
    }

    @Test
    void 고객정보가_수정되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");
        Customer savedEntity = repository.save(customer);

        // When
        savedEntity.setFirstName("hyeon");
        savedEntity.setLastName("so");

        // Then
        Customer selectedEntity = repository.findById(customer.getId()).get();
        assertThat(selectedEntity.getFirstName()).isEqualTo(savedEntity.getFirstName());
        assertThat(selectedEntity.getLastName()).isEqualTo(savedEntity.getLastName());
    }

    @Test
    void 고객정보가_삭제되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        repository.deleteById(customer.getId());

        // Then
        Optional<Customer> selectedEntity = repository.findById(customer.getId());
        assertThat(selectedEntity.isEmpty()).isEqualTo(true);
    }

    @Test
    void 고객정보가_전체삭제되는지_확인한다() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        repository.deleteAll();

        // Then
        List<Customer> selectedEntities = repository.findAll();
        assertThat(selectedEntities.size()).isEqualTo(0);
    }

}