package com.example.springbootjpa.mission1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @AfterEach
    void cleanUp(){
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Customer Create 테스트")
    void createCustomer(){
        List<Customer> customerList = new ArrayList<>();
        for(long i = 0; i<10; i++) {
            Customer customer = new Customer();
            customer.setId(i);
            customer.setFirstName(MessageFormat.format("User{0}", i));
            customer.setLastName(MessageFormat.format("Test{0}", i));
            customerRepository.save(customer);
            customerList.add(customer);
        }

        var foundList = customerRepository.findAll();

        assertThat(foundList).usingRecursiveFieldByFieldElementComparator().isEqualTo(customerList);
    }

    @Test
    @DisplayName("Customer Read 테스트")
    void readCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ella");
        customer.setLastName("Ma");
        customerRepository.save(customer);


        var foundResult = customerRepository.findById(customer.getId());

        assertThat(foundResult.isPresent()).isTrue();
        assertThat(foundResult.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer Update 테스트")
    void updateCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ella");
        customer.setLastName("Ma");
        customerRepository.save(customer);


        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        foundCustomer.setFirstName("Updated");
        customerRepository.save(foundCustomer);


        Customer updatedCustomer = customerRepository.findById(foundCustomer.getId()).get();

        assertThat(updatedCustomer).usingRecursiveComparison().isEqualTo(foundCustomer);
    }

    @Test
    @DisplayName("Customer Delete 테스트")
    void deleteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ella");
        customer.setLastName("Ma");
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        customerRepository.delete(foundCustomer);


        var checkDeleteResult = customerRepository.findById(customer.getId());

        assertThat(checkDeleteResult.isEmpty()).isTrue();
    }
}