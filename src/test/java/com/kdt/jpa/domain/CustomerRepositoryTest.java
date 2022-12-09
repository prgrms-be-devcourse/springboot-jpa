package com.kdt.jpa.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보가 저장된다.")
    void test_save() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        //when
        customerRepository.save(customer);

        //then
        Customer selectedEntity = customerRepository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @DisplayName("고객 정보가 수정된다.")
    void test_update() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");
        Customer entity = customerRepository.save(customer);

        //when
        entity.setFirstName("minji");
        entity.setLastName("kim");

        //then
        Customer selectedEntity = customerRepository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(selectedEntity.getLastName()).isEqualTo(entity.getLastName());
    }

    @Test
    @DisplayName("고객 정보 단건을 고객 아이디를 통해 조회한다.")
    void test_findById() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");
        customerRepository.save(customer);

        //when
        Customer selected = customerRepository.findById(customer.getId()).get();

        //then
        assertThat(customer.getId()).isEqualTo(selected.getId());
    }

    @Test
    @DisplayName("고객 정보 목록을 조회한다.")
    void test_findAll() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("taehee");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("minji");
        customer2.setLastName("kim");

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        //when
        List<Customer> selectedCustomers = customerRepository.findAll();

        //then
        assertThat(selectedCustomers.size()).isEqualTo(2);
    }
}