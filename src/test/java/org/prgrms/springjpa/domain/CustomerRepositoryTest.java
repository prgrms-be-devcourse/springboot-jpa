package org.prgrms.springjpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void 고객을_저장하고_조회할수있다() {
        //given
        Customer customer = new Customer("honggu", "kang");
        Customer savedCustomer = repository.save(customer);
        long customerId = savedCustomer.getId();
        //when
        Customer findCustomer = repository.findById(customerId).get();
        //then
        assertThat(findCustomer.getId()).isEqualTo(customerId);
    }

    @Test
    void 고객리스트를_조회할수있다() {
        //given
        Customer customer = new Customer("honggu", "kang");
        Customer secondCustomer = new Customer("woojin", "park");
        repository.save(customer);
        repository.save(secondCustomer);
        //when
        List<Customer> customers = repository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void 고객의_정보를_수정할수있다() {
        //given
        Customer customer = new Customer("honggu", "kang");
        Customer savedCustomer = repository.save(customer);
        long customerId = savedCustomer.getId();
        //when
        savedCustomer.setLastName("park");
        //then
        Customer updatedCustomer = repository.findById(customerId).get();
        assertThat(updatedCustomer.getLastName()).isEqualTo("park");
    }

    @Test
    void 고객을_삭제할수있다() {
        //given
        Customer customer = new Customer("honggu", "kang");
        Customer savedCustomer = repository.save(customer);
        long customerId = savedCustomer.getId();
        //when
        repository.deleteById(customerId);
        //then
        Optional<Customer> findCustomer = repository.findById(customerId);
        assertThat(findCustomer).isEmpty();
    }

    @Test
    void 고객리스트를_삭제할수있다() {
        //given
        Customer customer = new Customer("honggu", "kang");
        Customer secondCustomer = new Customer("woojin", "park");
        repository.save(customer);
        repository.save(secondCustomer);
        //when
        repository.deleteAll();
        //then
        List<Customer> customers = repository.findAll();
        assertThat(customers).isEmpty();
    }

}