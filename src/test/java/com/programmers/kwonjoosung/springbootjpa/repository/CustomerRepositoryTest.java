package com.programmers.kwonjoosung.springbootjpa.repository;

import com.programmers.kwonjoosung.springbootjpa.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@RequiredArgsConstructor -> 안되는 이유가 뭘까?
@Slf4j
@DataJpaTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer generateTestCustomer() {
        return new Customer("joosung", "kwon");
    }

    @Test
    @DisplayName("Customer 저장 및 조회 테스트")
    void saveTest() {

        //given
        Customer customer = generateTestCustomer();
        //when
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        //then
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
        log.info("savedCustomer : firstName {}, lastName {}", savedCustomer.getFirstName(), savedCustomer.getLastName());
    }

    @Test
    @DisplayName("Customer 수정 테스트")
    void updateTest() {

        //given
        Customer customer = generateTestCustomer();
        Customer savedCustomer = customerRepository.save(customer);
        log.info("savedCustomer : firstName {}, lastName {}", savedCustomer.getFirstName(), savedCustomer.getLastName());

        //when
        savedCustomer.changeFirstName("SUNGJOO");
        savedCustomer.changeLastName("KIM");
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        //then
        assertThat(updatedCustomer.getFirstName()).isEqualTo("SUNGJOO");
        assertThat(updatedCustomer.getLastName()).isEqualTo("KIM");
        log.info("updatedCustomer : firstName {}, lastName {}", savedCustomer.getFirstName(), savedCustomer.getLastName());
    }

    @Test
    @DisplayName("Customer 삭제 테스트")
    void delteTest() {

        //given
        Customer customer = generateTestCustomer();
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        log.info("savedCustomer : firstName {}, lastName {}", savedCustomer.getFirstName(), savedCustomer.getLastName());
        //when
        customerRepository.delete(customer);
        //then
        assertThat(customerRepository.findById(customer.getId()).isPresent()).isFalse();
    }

}