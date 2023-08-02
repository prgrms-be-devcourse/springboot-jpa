package com.example.springjpamission.customer.domain;

import com.example.springjpamission.customer.service.CustomerService;
import com.example.springjpamission.customer.service.dto.CustomerResponse;
import com.example.springjpamission.customer.service.dto.CustomerResponses;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;
    Long setUpCustomerId;

    @BeforeEach
    void setUp(){
        Customer customer = new Customer(new Name("영운", "윤"));
        customerRepository.save(customer);
        setUpCustomerId = customer.getId();
    }

//    @Test
//    void saveCustomer() {
//        //given
//        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest("별", "김");
//
//        //when
//        CustomerResponse customerResponse = customerService.saveCustomer(saveCustomerRequest);
//
//        //then
//        assertThat(customerResponse.firstName()).isEqualTo("별");
//        assertThat(customerResponse.lastName()).isEqualTo("김");
//    }

    @Test
    void updateCustomer() {
        //given
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(setUpCustomerId, "별", "김");

        //when
        CustomerResponse customerResponse = customerService.updateCustomer(updateCustomerRequest);

        //then
        assertThat(customerResponse.firstName()).isEqualTo("별");
        assertThat(customerResponse.lastName()).isEqualTo("김");
    }

//    @Test
//    void findAll() {
//        //when
//        CustomerResponses findCustomers = customerService.findAll();
//
//        //then
//        assertThat(findCustomers.customerResponses().size()).isEqualTo(1);
//    }

    @Test
    void deleteById() {
        //when
        customerService.deleteById(setUpCustomerId);

        //then
        List<Customer> findCustomers = customerRepository.findAll();
        assertThat(findCustomers.size()).isEqualTo(0);
    }

}
