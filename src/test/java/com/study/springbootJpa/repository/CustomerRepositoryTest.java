package com.study.springbootJpa.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import com.study.springbootJpa.domain.Customer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

        customerRepository.save(customer1);
        customerRepository.save(customer2);
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
        var savedCustomer = customerRepository.save(customer);
        var foundCustomer = customerRepository.findById(3L);
        //then
        assertThat(foundCustomer).isPresent();
        assertThat(savedCustomer).isEqualTo(foundCustomer.get());
    }


    @Test
    @DisplayName("모든 Customer의 List가 return 되어야함 ")
    void findAll_test() {
        //given
        List<Customer> customers = List.of(customer1, customer2);
        //when
        var foundCustomers = customerRepository.findAll();
        //then
        assertThat(foundCustomers.size()).isEqualTo(2);
        assertThat(foundCustomers).isEqualTo(customers);
    }

    @Test
    @DisplayName("해당 Id의 Customer가 return 되어야함")
    void findById_test() {
        //given
        var id = customer1.getId();
        //when
        var foundCustomer = customerRepository.findById(id);
        //then
        assertThat(foundCustomer).isPresent();
        assertThat(customer1.getId()).isEqualTo(foundCustomer.get().getId());
    }

    @Test
    @DisplayName("없는 Id 조회시 Empty가 반환되어야함")
    void findById_fail_test() {
        //given
        var id = 100L;
        //when
        var foundCustomer = customerRepository.findById(id);
        //then
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    @DisplayName("해당 Id의 Customer가 삭제 되어야함")
    void deleteById_test() {
        //given
        var id = customer1.getId();
        //when
        customerRepository.deleteById(id);
        //then
        var deletedCustomer = customerRepository.findById(id);
        assertThat(deletedCustomer).isEmpty();
    }

    @Test
    @DisplayName("Customer의 FirstName과 LastName이 수정되어야함")
    void update_test() {
        //given
        var foundCustomer = customerRepository.findById(1L).get();
        foundCustomer.setFirstName("test");
        foundCustomer.setLastName("update");
        //when
        var updatedCustomer = customerRepository.findById(1L);
        //then
        assertThat(updatedCustomer).isPresent();
        assertThat(updatedCustomer.get()).isEqualTo(foundCustomer);
    }
}