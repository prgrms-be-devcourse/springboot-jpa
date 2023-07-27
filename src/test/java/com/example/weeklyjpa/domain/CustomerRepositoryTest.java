package com.example.weeklyjpa.domain;

import com.example.weeklyjpa.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("yejin","shin");
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장한다.")
    void saveTest(){
        // when
        Customer savedCustomer = customerRepository.save(customer);
        // then
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @DisplayName("전체 고객을 조회한다.")
    void findAllTest() {
        // when
        customerRepository.save(customer);
        List<Customer> result = customerRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("id로 고객을 조회한다.")
    void findByIdTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        long customerId = savedCustomer.getId();
        // when
        Customer findCustomerById = customerRepository.findById(customerId).get();

        // then
        assertThat(findCustomerById).isEqualTo(savedCustomer);
    }

    @Test
    @DisplayName("고객의 정보를 업데이트한다.")
    void updateTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        String updateName = "zara";
        savedCustomer.updateFirstName(updateName);

        // then
        assertThat(savedCustomer.getFirstName()).isEqualTo(updateName);
    }

    @Test
    @DisplayName("고객 id로 고객을 삭제한다.")
    void deleteByIdTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        long customerId = savedCustomer.getId();

        // when
        customerRepository.deleteById(customerId);

        // then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }
}