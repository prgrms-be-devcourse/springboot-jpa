package com.prgms.springbootjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgms.springbootjpa.domain.customer.Customer;
import com.prgms.springbootjpa.domain.customer.Name;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


@SpringJUnitConfig(com.prgms.springbootjpa.config.DataSourceConfig.class)
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, new Name("eunhyuk", "bahng"));
    }

    @Test
    @DisplayName("고객정보 저장")
    void testSaveSuccess() {
        //Given
        //When
        customerRepository.save(customer);

        //Then
        var selectedEntity = customerRepository.findById(customer.getId()).get();
        assertAll(
            () -> assertThat(selectedEntity).isNotNull(),
            () -> assertThat(selectedEntity.getId()).isEqualTo(customer.getId()),
            () -> assertThat(selectedEntity.getName()).isEqualTo(customer.getName())
        );
    }

    @Test
    @DisplayName("고객정보 수정")
    void testUpdateSuccess() {
        //Given
        var entity = customerRepository.save(customer);

        //When
        entity.changeName(new Name("honggu", "kang"));

        //Then
        var selectedEntity = customerRepository.findById(entity.getId()).get();
        assertAll(
            () -> assertThat(selectedEntity).isNotNull(),
            () -> assertThat(selectedEntity.getId()).isEqualTo(entity.getId()),
            () -> assertThat(selectedEntity.getName()).isEqualTo(entity.getName())
        );
    }

    @Test
    @DisplayName("단건 조회")
    void testSelectOneSuccess() {
        //Given
        customerRepository.save(customer);

        //When
        var selected = customerRepository.findById(customer.getId()).get();

        //Then
        assertThat(selected.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("다건 조회")
    void testSelectManySuccess() {
        //Given
        var customer2 = new Customer(2L, new Name("honggu", "kang"));
        customerRepository.saveAll(Lists.newArrayList(customer, customer2));

        //When
        var selectCustomers = customerRepository.findAll();

        //Then
        assertThat(selectCustomers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객정보 삭제")
    void testDeleteSuccess() {
        //Given
        customerRepository.save(customer);

        //When
        customerRepository.deleteById(customer.getId());

        //Then
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }
}