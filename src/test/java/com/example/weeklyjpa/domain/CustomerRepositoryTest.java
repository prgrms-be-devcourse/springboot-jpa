package com.example.weeklyjpa.domain;

import com.example.weeklyjpa.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    private Customer savedCustomer;

    @BeforeEach
    void setUp() {
        customer = new Customer("yejin","shin");
        savedCustomer = customerRepository.save(customer);
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장한다.")
    void saveTest(){
        // then
        Assertions.assertEquals(savedCustomer.getFirstName(),customer.getFirstName());
    }

    @Test
    @DisplayName("전체 고객을 조회한다.")
    void findAllTest() {
        // when
        List<Customer> result = customerRepository.findAll();

        // then
        Assertions.assertEquals(result.size(),1);
    }

    @Test
    @DisplayName("id로 고객을 조회한다.")
    void findByIdTest() {
        // given
        long customerId = savedCustomer.getId();
        // when
        Customer findCustomerById = customerRepository.findById(customerId).get();

        // then
        Assertions.assertEquals(findCustomerById,savedCustomer);
    }

    @Test
    @DisplayName("고객의 정보를 업데이트한다.")
    void updateTest() {
        // when
        String updateName = "zara";
        savedCustomer.updateFirstName(updateName);

        // then
        Assertions.assertEquals(savedCustomer.getFirstName(),updateName);
    }

    @Test
    @DisplayName("고객 id로 고객을 삭제한다.")
    void deleteByIdTest() {
        // given
        long customerId = savedCustomer.getId();

        // when
        customerRepository.deleteById(customerId);

        // then
        Assertions.assertEquals(customerRepository.findAll().size(),0);
    }
}