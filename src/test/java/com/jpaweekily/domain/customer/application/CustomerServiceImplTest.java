package com.jpaweekily.domain.customer.application;

import com.jpaweekily.domain.customer.dto.CustomerRequest;
import com.jpaweekily.domain.customer.dto.CustomerResponse;
import com.jpaweekily.domain.customer.dto.CustomerUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createTest() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("weon", "geonhee");

        //when
        Long id = customerService.create(customerRequest);

        //then
        assertThat(id).isNotNull();
    }

    @Test
    void findByIdTest() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("weon2", "geonhee");
        Long id = customerService.create(customerRequest);

        //when
        CustomerResponse customerById = customerService.findCustomerById(id);

        //then
        assertThat(customerById.firstName()).isEqualTo("weon2");
    }

    @Test
    void findAllTest() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("weon", "geonhee");
        CustomerRequest customerRequest2 = new CustomerRequest("god", "geonhee");
        customerService.create(customerRequest);
        customerService.create(customerRequest2);

        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerResponse> customers = customerService.findCustomersWithPaging(pageable);

        //then
        assertThat(customers.getTotalElements()).isEqualTo(2);
    }

    @Test
    void updateTest() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("weon", "geonhee");
        Long id = customerService.create(customerRequest);
        CustomerUpdate customerUpdate = new CustomerUpdate("god", "sin");

        //when
        customerService.update(id, customerUpdate);
        CustomerResponse updated = customerService.findCustomerById(id);

        //then
        assertThat(customerUpdate.firstName()).isEqualTo(updated.firstName());
    }

    @Test
    void deleteTest() {
        CustomerRequest customerRequest = new CustomerRequest("weon", "geonhee");
        Long id = customerService.create(customerRequest);

        //when
        customerService.delete(id);

        //then
        assertThatThrownBy(() -> customerService.findCustomerById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
