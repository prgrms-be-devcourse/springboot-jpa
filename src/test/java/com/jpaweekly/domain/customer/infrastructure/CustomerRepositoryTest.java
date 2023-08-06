package com.jpaweekly.domain.customer.infrastructure;

import com.jpaweekly.domain.customer.application.CustomerService;
import com.jpaweekly.domain.customer.application.CustomerServiceImpl;
import com.jpaweekly.domain.customer.dto.CustomerRequest;
import com.jpaweekly.domain.customer.dto.CustomerResponse;
import com.jpaweekly.domain.customer.dto.CustomerUpdate;
import jakarta.persistence.EntityNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(CustomerServiceImpl.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createTest() {
        //given
        CustomerRequest request = Instancio.create(CustomerRequest.class);

        //when
        Long id = customerService.create(request);

        //then
        assertThat(id).isNotNull();
    }

    @Test
    void findByIdTest() {
        //given
        CustomerRequest request = Instancio.create(CustomerRequest.class);
        Long id = customerService.create(request);

        //when
        CustomerResponse storedCustomer = customerService.findCustomerById(id);

        //then
        assertThat(id).isEqualTo(storedCustomer.id());
    }

    @Test
    void findAllTest() {
        //given
        int testSize = 33;
        List<CustomerRequest> requests = Instancio.ofList(CustomerRequest.class).size(testSize).create();
        requests.forEach(customerService::create);

        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerResponse> response = customerService.findCustomersWithPaging(pageable);

        //then
        assertThat(response.getTotalElements()).isEqualTo(testSize);
    }

    @Test
    void updateTest() {
        //given
        CustomerRequest request = Instancio.create(CustomerRequest.class);
        Long id = customerService.create(request);
        CustomerUpdate customerUpdate = Instancio.create(CustomerUpdate.class);

        //when
        customerService.update(id, customerUpdate);
        CustomerResponse updated = customerService.findCustomerById(id);

        //then
        assertThat(customerUpdate.firstName()).isEqualTo(updated.firstName());
    }

    @Test
    void deleteTest() {
        CustomerRequest request = Instancio.create(CustomerRequest.class);
        Long id = customerService.create(request);

        //when
        customerService.delete(id);

        //then
        assertThatThrownBy(() -> customerService.findCustomerById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
