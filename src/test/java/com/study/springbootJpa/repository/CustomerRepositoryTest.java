package com.study.springbootJpa.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import com.study.springbootJpa.domain.Customer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1 = new Customer();
    private Customer customer2 = new Customer();

    @BeforeEach
    void setUp() {
        customer1.setId(1L);
        customer1.setFirstName("jjin");
        customer1.setLastName("bbang");

        customer2.setId(2L);
        customer2.setFirstName("ho");
        customer2.setLastName("bbang");
    }

    @Test
    @DisplayName("Customer가 저장되어야함")
    void save_test() {
        //given
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("hyeb");
        customer.setLastName("park");
        //when
        var saveCustomer = customerRepository.save(customer);
        var findCustomer = customerRepository.findById(3L);
        //then
        assertThat(saveCustomer).isEqualTo(findCustomer.get());
    }


    @Test
    @DisplayName("모든 Customer List가 return 되어야함 ")
    void findAll_test() {
        //given
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        List<Customer> customers = List.of(customer1, customer2);
        //when
        var findAll = customerRepository.findAll();
        //then
        assertThat(findAll.size()).isEqualTo(2);
        assertThat(findAll).isEqualTo(customers);
    }

    @Test
    @DisplayName("해당 Id의 Customer가 return 되어야함")
    void findById_test() {
        //given
        customerRepository.save(customer1);
        var id = customer1.getId();
        //when
        var findCustomerOrEmpty = customerRepository.findById(id);
        //then
        assertThat(findCustomerOrEmpty).isNotEmpty();
        assertThat(customer1.getId()).isEqualTo(findCustomerOrEmpty.get().getId());
    }

    @Test
    @DisplayName("없는 Id 조회시 Optional.Empty가 반환되어야함")
    void findById_fail_test() {
        //given
        var id = 100L;
        //when
        var findCustomerOrEmpty = customerRepository.findById(id);
        //then
        assertThat(findCustomerOrEmpty).isEmpty();
    }

    @Test
    @DisplayName("해당 Id의 Customer가 삭제 되어야함")
    void deleteById_test() {
        //given
        customerRepository.save(customer1);
        var id = customer1.getId();
        //when
        customerRepository.deleteById(id);
        //then
        var customer = customerRepository.findById(id);
        assertThat(customer).isEmpty();
    }

    @Test
    @DisplayName("Customer의 FirstName과 LastName이 수정되어야함")
    void update_test() {
        //given
        customerRepository.save(customer1);
        var customer = customerRepository.findById(1L).get();
        customer.setFirstName("test");
        customer.setLastName("update");
        //when
        var update = customerRepository.findById(1L).get();
        //then
        assertThat(update).isEqualTo(customer);
    }

}
