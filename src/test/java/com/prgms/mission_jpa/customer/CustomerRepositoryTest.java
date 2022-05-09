package com.prgms.mission_jpa.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void cleanup(){
        customerRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"a,a","b,b","c,c"})
    void 고객을_저장할_수_있다(String firstName,String lastName) {
        //given
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        //when
        Customer savedCustomer = customerRepository.save(customer);
        //then
        assertThat(savedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 고객정보를_수정할_수_있다() {
        //given
        Customer customer = Customer.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        //when
        savedCustomer.setFirstName("yongsu");
        savedCustomer.setLastName("kang");
        //then
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).get();
        assertThat(updatedCustomer).usingRecursiveComparison().isEqualTo(savedCustomer);
    }

    @Test
    void 고객을_삭제할_수_있다(){
        //given
        Customer customer = Customer.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        //when
        customerRepository.deleteById(savedCustomer.getId());
        //then
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());
        assertThat(findCustomer).isEmpty();
    }

    @Test
    void 전체고객을_삭제할_수_있다(){
        //given
        customerRepository.saveAll(customers());
        List<Customer> findAllCustomers = customerRepository.findAll();
        assertThat(findAllCustomers).hasSize(10);
        //when
        customerRepository.deleteAll();
        //then
        List<Customer> afterDeleteCustomers = customerRepository.findAll();
        assertThat(afterDeleteCustomers).isEmpty();
    }

    private List<Customer> customers(){
        List<Customer> customers = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            customers.add(Customer.builder().firstName("yongsu_" + i).lastName("kang_" + i).build());
        }
        return customers;
    }
}