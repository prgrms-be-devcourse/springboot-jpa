package com.jpaweekly.domain.customer.application;

import com.jpaweekly.domain.customer.Customer;
import com.jpaweekly.domain.customer.CustomerConverter;
import com.jpaweekly.domain.customer.dto.CustomerRequest;
import com.jpaweekly.domain.customer.dto.CustomerResponse;
import com.jpaweekly.domain.customer.dto.CustomerUpdate;
import com.jpaweekly.domain.customer.infrastructrue.CustomerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static MockedStatic<CustomerConverter> customerConverter;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeAll
    public static void setUp() {
        customerConverter = mockStatic(CustomerConverter.class);
    }

    @AfterAll
    public static void tearDown() {
        customerConverter.close();
    }

    @Test
    void createCustomerTest() {
        //given
        Customer customer = Instancio.create(Customer.class);
        Long customerId = customer.getId();
        CustomerRequest request = Instancio.create(CustomerRequest.class);

        given(CustomerConverter.convertRequestToEntity(request)).willReturn(customer);
        given(customerRepository.save(customer)).willReturn(customer);

        //when
        Long id = customerService.create(request);

        //then
        assertThat(customerId).isEqualTo(id);
    }

    @Test
    void findByIdTest() {
        Customer customer = Instancio.create(Customer.class);
        Long customerId = customer.getId();
        CustomerResponse response = Instancio.create(CustomerResponse.class);

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        given(CustomerConverter.convertEntityToResponse(customer)).willReturn(response);

        //when
        CustomerResponse storedCustomer = customerService.findCustomerById(customerId);

        //then
        assertThat(response).isEqualTo(storedCustomer);
    }

    @Test
    void findAllTest() {
        //given
        int testSize = 55;
        int page = 0;
        int pageSize = 10;

        Page<Customer> customers = new PageImpl<>(Instancio.ofList(Customer.class).size(testSize).create());
        Pageable pageable = PageRequest.of(page, pageSize);

        given(customerRepository.findAll(pageable)).willReturn(customers);

        //when
        Page<CustomerResponse> storedCustomers = customerService.findCustomersWithPaging(pageable);

        //then
        assertThat(storedCustomers.getTotalElements()).isEqualTo(testSize);
    }

    @Test
    void updateTest() {
        //given
        Customer customer = Instancio.create(Customer.class);
        Long customerId = customer.getId();
        CustomerUpdate request = Instancio.create(CustomerUpdate.class);
        CustomerResponse response = new CustomerResponse(customerId, request.firstName(), request.lastName());

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        given(CustomerConverter.convertEntityToResponse(customer)).willReturn(response);

        //when
        customerService.update(customerId, request);

        //then
        assertThat(request.firstName()).isEqualTo(response.firstName());
    }

    @Test
    void deleteTest() {
        //given
        Customer customer = Instancio.create(Customer.class);
        Long customerId = customer.getId();

        //when
        doNothing().when(customerRepository).deleteById(customerId);

        //then
        assertThatNoException().isThrownBy(() -> customerService.delete(customerId));
    }

}
