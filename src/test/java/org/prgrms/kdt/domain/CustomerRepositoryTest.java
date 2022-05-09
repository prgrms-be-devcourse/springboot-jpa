package org.prgrms.kdt.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@Slf4j
@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer newCustomer = new Customer("moly", "holy");

    @Test
    @DisplayName("고객을 등록할 수 있다.")
    void testInsert() {
        // when
        Customer customer = customerRepository.save(newCustomer);

        // then
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        log.info("customer - name : {} {}", foundCustomer.getLastName(), foundCustomer.getFirstName());
        assertThat(foundCustomer, is(customer));
    }

    @Test
    @DisplayName("고객을 조회할 수 있다.")
    void testFindAll() {
        // given
        Customer customer = customerRepository.save(newCustomer);

        // when
        List<Customer> customers = customerRepository.findAll();

        // then
        log.info("customer-list : {}", customers);
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasItem(customer));
    }
    
    @Test
    @DisplayName("고객 정보를 수정할 수 있다.")
    void testUpdate() {
        // given
        Customer customer = customerRepository.save(newCustomer);

        // when
        customer.changeFirstName("hoit");
        customer.changeLastName("dooly");
    
        // then
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        log.info("Updated customer : {}", updatedCustomer);
        assertThat(customer, samePropertyValuesAs(updatedCustomer));
        assertThat(customer, is(updatedCustomer));
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // given
        Customer customer = customerRepository.save(newCustomer);

        // when
        customerRepository.delete(customer);

        // then
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assertThat(deletedCustomer.isEmpty(), is(true));
    }
}