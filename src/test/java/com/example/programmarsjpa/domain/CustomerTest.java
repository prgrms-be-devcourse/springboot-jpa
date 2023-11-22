package com.example.programmarsjpa.domain;


import com.example.programmarsjpa.domain.customer.Customer;
import com.example.programmarsjpa.domain.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CustomerTest {

    @Autowired
    CustomerRepository customerRepository;

    @DisplayName("고객을 생성할 수 있다.")
    @Test
    void createCustomerTest() {
        //Given
        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();
        //When
        Customer entityCustomer = customerRepository.save(customer);

        //Then
        assertThat(customer.getFirstName()).isEqualTo(entityCustomer.getFirstName());
        assertThat(customer.getLastName()).isEqualTo(entityCustomer.getLastName());
        assertThat(entityCustomer.getId()).isNotNull();
    }

    @DisplayName("기존 고객을 수정할 수 있다.")
    @Test
    void updateCustomerTest() {
        //Given
        String editFirstName = "kim";
        String editLastName = "rabbit";
        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();
        Customer entityCustomer = customerRepository.save(customer);
        //When
        customer.setFirstName(editFirstName);
        customer.setLastName(editLastName);

        //Then
        Customer changeCustomer = customerRepository.findById(entityCustomer.getId()).orElseThrow();
        assertThat(changeCustomer.getFirstName()).isEqualTo(editFirstName);
        assertThat(changeCustomer.getLastName()).isEqualTo(editLastName);
    }

    @DisplayName("고객을 찾을 수 있다.")
    @Test
    void findCustomerTest() {
        //Given
        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();
        Customer entityCustomer = customerRepository.save(customer);
        //When
        Optional<Customer> optionalCustomer = customerRepository.findById(entityCustomer.getId());
        //Then
        assertThat(optionalCustomer).isPresent();
        assertThat(optionalCustomer.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(optionalCustomer.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @DisplayName("고객을 삭제할 수 있다.")
    @Test
    void test(){
        //Given
        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();
        Customer entityCustomer = customerRepository.save(customer);
        //When
        customerRepository.delete(customer);
        //Then
        assertThat(customerRepository.findById(entityCustomer.getId())).isEmpty();
     }
}
