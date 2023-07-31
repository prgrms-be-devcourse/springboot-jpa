package com.programmers.springbootjpa.domain.mission1.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("hyemin", "Kim");
    }

    @DisplayName("customer를 저장한다")
    @Test
    void testSave() {
        //given
        //when
        Customer savedCustomer = customerRepository.save(customer);

        //then
        assertThat(savedCustomer.getFirstName()).isEqualTo("hyemin");
        assertThat(savedCustomer.getLastName()).isEqualTo("Kim");
    }

    @DisplayName("customer를 수정한다")
    @Test
    void testUpdate() {
        //given
        Customer savedCustomer = customerRepository.save(customer);

        //when
        savedCustomer.updateFirstName("Lee");
        savedCustomer.updateLastName("ham");

        //then
        assertThat(savedCustomer.getFirstName()).isEqualTo("Lee");
        assertThat(savedCustomer.getLastName()).isEqualTo("ham");
    }

    @DisplayName("customer를 id로 조회한다")
    @Test
    void testFindById() {
        //given
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer result = customerRepository.findById(savedCustomer.getId()).get();

        //then
        assertThat(result.getFirstName()).isEqualTo("hyemin");
        assertThat(result.getLastName()).isEqualTo("Kim");
    }

    @DisplayName("저장된 customer를 모두 조회한다")
    @Test
    void testFindAll() {
        //given
        customerRepository.save(customer);

        Customer customer2 = new Customer("min", "Lee");

        customerRepository.save(customer2);

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers).hasSize(2);
    }

    @DisplayName("customer를 삭제한다")
    @Test
    void testDelete() {
        //given
        Customer savedCustomer = customerRepository.save(customer);

        //when
        customerRepository.delete(savedCustomer);
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers).isEmpty();
    }

    @DisplayName("저장된 customer를 모두 삭제한다")
    @Test
    void testDeleteAll() {
        //given
        customerRepository.save(customer);

        Customer customer2 = new Customer("min", "Lee");

        customerRepository.save(customer2);

        //when
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers).isEmpty();
    }
}