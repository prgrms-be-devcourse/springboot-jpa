package com.will.jpapractice.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 고객정보_저장_확인() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("will");
        customer.setLastName("kim");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 고객정보_수정_확인() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("will");
        customer.setLastName("kim");
        Customer entity = repository.save(customer);

        // When
        entity.setFirstName("jake");
        entity.setLastName("joe");

        // Then
        Customer selectedEntity = repository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(selectedEntity.getLastName()).isEqualTo(entity.getLastName());
    }

    @Test
    void 단건조회_확인() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("will");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        Customer selectedEntity = repository.getById(customer.getId());

        // Then
        assertThat(selectedEntity.getId()).isEqualTo(customer.getId());
    }

    @Test
    void 리스트조회_확인() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("will");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("jake");
        customer2.setLastName("joe");

        repository.saveAll(Lists.newArrayList(customer1, customer2));

        // When
        List<Customer> seletedCustomers = repository.findAll();

        // Then
        assertThat(seletedCustomers.size()).isEqualTo(2);
    }

    @Test
    void 고객정보_삭제_확인() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("will");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        repository.delete(customer);

        // Then
        assertThat(repository.findById(customer.getId())).isEmpty();
    }
}