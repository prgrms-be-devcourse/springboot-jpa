package com.pgms.jpa.domain;

import com.pgms.jpa.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CustomerCrudTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("고객을 저장한다.")
    void saveTest() {
        // given
        Customer customer = new Customer("앨런", 30);

        // when
        Long savedId = customerService.join(customer);
        Customer findCustomer = customerService.findOne(savedId);

        // then
        Assertions.assertThat(findCustomer.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("전체 고객을 조회한다.")
    void findAllTest() {
        //given
        Customer customer1 = new Customer("앨런", 30);
        Customer customer2 = new Customer("지수", 21);
        customerService.join(customer1);
        customerService.join(customer2);

        //when
        List<Customer> customers = customerService.findAll();

        //then
        Assertions.assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("id로 고객을 조회할 수 있다.")
    void findByIdTest() {
        // given
        Customer customer = new Customer("앨런", 30);
        Long savedId = customerService.join(customer);

        // when
        Customer findCustomer = customerService.findOne(savedId);

        // then
        Assertions.assertThat(findCustomer.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("id로 고객을 삭제할 수 있다.")
    void deleteCustomerByIdTest() {
        // given
        Customer customer = new Customer("앨런", 30);
        Long savedId = customerService.join(customer);

        // when
        customerService.delete(savedId);
        List<Customer> findCustomers = customerService.findAll();

        // then
        Assertions.assertThat(findCustomers).isEmpty();
    }

    @Test
    @DisplayName("전체 고객을 삭제할 수 있다.")
    void deleteCustomersTest() {
        // given
        Customer customer1 = new Customer("앨런", 30);
        Customer customer2 = new Customer("지수", 21);
        customerService.join(customer1);
        customerService.join(customer2);

        // when
        customerService.deleteAll();
        List<Customer> findCustomers = customerService.findAll();

        // then
        Assertions.assertThat(findCustomers).isEmpty();
    }
}
