package com.example.springjpamission.customer.domain;

import com.example.springjpamission.customer.service.CustomerService;
import com.example.springjpamission.customer.service.dto.CustomerRespone;
import com.example.springjpamission.customer.service.dto.CustomerRespones;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    Long setUpCustomerId;

    @BeforeEach
    void setUp(){
        Customer customer = new Customer(new Name("영운", "윤"));
        setUpCustomerId = customer.getId();
        customerRepository.save(customer);
    }

    @Test
    void saveCustomer() {
        //given
        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest("별", "김");

        //when
        CustomerRespone customerRespone = customerService.saveCustomer(saveCustomerRequest);

        //then
        assertThat(customerRespone.firstName()).isEqualTo("별");
        assertThat(customerRespone.lastName()).isEqualTo("김");
    }

    @Test
    void updateCustomer() {
        //given
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(setUpCustomerId, "별", "김");

        //when
        CustomerRespone customerRespone = customerService.updateCustomer(updateCustomerRequest);

        //then
        assertThat(customerRespone.firstName()).isEqualTo("별");
        assertThat(customerRespone.lastName()).isEqualTo("김");
    }

    @Test
    void findAll() {
        //when
        CustomerRespones findCustomers = customerService.findAll();

        //then
        assertThat(findCustomers.customerRespones().size()).isEqualTo(1);
    }

    @Test
    void deletById() {
        //when
        customerService.deletById(setUpCustomerId);

        //then
        List<Customer> findCustomers = customerRepository.findAll();
        assertThat(findCustomers.size()).isEqualTo(0);
    }
}
