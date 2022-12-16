package com.prgms.springbootjpa.repository;

import com.prgms.springbootjpa.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class CustomerRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(CustomerRepositoryTest.class);
    private static final String FIRST_NAME = "티나";
    private static final String LAST_NAME = "정";
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void eachSetup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Customer를 저장하면 저장된 정보와 입력한 정보가 일치한다.")
    void 소비자를저장한다() {
        // given
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        // when
        Customer savedCustomer = customerRepository.save(customer);
        logger.info(savedCustomer.getKoreanName());
        // then
        assertEquals(customer,savedCustomer);
    }

    @Test
    @DisplayName("저장된 Customer를 ID로 조회시 성공한다.")
    void 소비자를저장하고_ID로조회한다() {
        // given
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        // when
        Customer savedCustomer = customerRepository.save(customer);
        Optional<Customer> findCustomer = customerRepository.findById(1);
        // then
        assertTrue(findCustomer.isPresent());
        assertEquals(savedCustomer, findCustomer.get());
    }

    @Test
    @DisplayName("저장된 Customer를 이름으로 조회시 성공한다.")
    void 소비자를저장하고_이름으로조회한다() {
        // given
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        // when
        Customer savedCustomer = customerRepository.save(customer);
        List<Customer> findCustomerList = customerRepository.findCustomerByFirstName(FIRST_NAME);
        // then
        assertEquals(1, findCustomerList.size());
        assertEquals(savedCustomer, findCustomerList.get(0));
    }

    @Test
    @DisplayName("저장된 Customer의 이름을 수정하고 이름으로 조회시 성공한다.")
    void 소비자를저장하고_수정한후_이름으로조회한다() {
        // given
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        // when
        Customer savedCustomer = customerRepository.save(customer);
        String newName = "미구";
        savedCustomer.modifyFirstName(newName);
        List<Customer> findCustomerList = customerRepository.findCustomerByFirstName(newName);
        // then
        assertEquals(1, findCustomerList.size());
        assertEquals(savedCustomer, findCustomerList.get(0));
        logger.info(findCustomerList.get(0).toString());
    }
    @Test
    @DisplayName("저장된 Customer를 삭제하면 테이블의 ROW수는 0이다.")
    void 소비자를삭제한다() {
        // given
        Customer customer = new Customer(FIRST_NAME, LAST_NAME);
        // when
        customerRepository.save(customer);
        customerRepository.delete(customer);
        long count = customerRepository.count();
        // then
        assertEquals(0L, count);
    }
}