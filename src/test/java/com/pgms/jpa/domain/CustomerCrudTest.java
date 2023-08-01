package com.pgms.jpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class CustomerCrudTest {

    @Autowired
    private DefaultCustomerRepository defaultCustomerRepository;

    @AfterEach
    void tearDown() {
        defaultCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장한다.")
    void saveTest() {
        // given
        Customer customer = new Customer("앨런", 30);

        // when
        Long savedId = defaultCustomerRepository.save(customer);
        Customer findCustomer = defaultCustomerRepository.findById(savedId).get();

        // then
        Assertions.assertThat(findCustomer.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("전체 고객을 조회한다.")
    void findAllTest() {
        //given
        Customer customer1 = new Customer("앨런", 30);
        Customer customer2 = new Customer("지수", 21);
        defaultCustomerRepository.save(customer1);
        defaultCustomerRepository.save(customer2);

        //when
        List<Customer> customers = defaultCustomerRepository.findAll();

        //then
        Assertions.assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("id로 고객을 조회할 수 있다.")
    void findByIdTest() {
        // given
        Customer customer = new Customer("앨런", 30);
        Long savedId = defaultCustomerRepository.save(customer);

        // when
        Customer findCustomer = defaultCustomerRepository.findById(savedId).get();

        // then
        Assertions.assertThat(findCustomer.getId()).isEqualTo(savedId);
    }

    @Transactional
    @Test
    @DisplayName("고객 이름을 수정할 수 있다.")
    void updateCustomerTest() {
        // given
        Customer customer = new Customer("앨런", 30);
        Long savedId = defaultCustomerRepository.save(customer);


        // when
        Customer findCustomer = defaultCustomerRepository.findById(savedId).get();
        findCustomer.changeName("태현");
        Customer findCustomerB = defaultCustomerRepository.findById(savedId).get();

        // then
        Assertions.assertThat(findCustomerB.getName()).isEqualTo("태현");
    }


    @Test
    @DisplayName("id로 고객을 삭제할 수 있다.")
    void deleteCustomerByIdTest() {
        // given
        Customer customer = new Customer("앨런", 30);
        Long savedId = defaultCustomerRepository.save(customer);

        // when
        defaultCustomerRepository.delete(savedId);
        List<Customer> findCustomers = defaultCustomerRepository.findAll();

        // then
        Assertions.assertThat(findCustomers).isEmpty();
    }

    @Test
    @DisplayName("전체 고객을 삭제할 수 있다.")
    void deleteCustomersTest() {
        // given
        Customer customer1 = new Customer("앨런", 30);
        Customer customer2 = new Customer("지수", 21);
        defaultCustomerRepository.save(customer1);
        defaultCustomerRepository.save(customer2);

        // when
        defaultCustomerRepository.deleteAll();
        List<Customer> findCustomers = defaultCustomerRepository.findAll();

        // then
        Assertions.assertThat(findCustomers).isEmpty();
    }
}
