package com.blackdog.springbootjpa;

import com.blackdog.springbootjpa.domain.customer.dto.CustomerCreateRequest;
import com.blackdog.springbootjpa.domain.customer.dto.CustomerResponse;
import com.blackdog.springbootjpa.domain.customer.dto.CustomerUpdateRequest;
import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.domain.customer.repository.CustomerRepository;
import com.blackdog.springbootjpa.domain.customer.service.CustomerService;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.CustomerName;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setCustomer() {
        customer = Customer.builder()
                .customerName(new CustomerName("Kim"))
                .age(new Age(34))
                .email(new Email("kim@naver.com"))
                .build();
    }

    @Test
    @DisplayName("고객(Customer)를 생성한다.")
    void createCustomer_Dto_ReturnCustomerResponse() {
        //given
        CustomerCreateRequest request = new CustomerCreateRequest("Park", 23, "park@gmail.com");

        //when
        CustomerResponse response = customerService.createCustomer(request);

        //then
        Customer customer = customerRepository.findById(response.id()).get();

        assertThat(response.id()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("고객(Customer)를 수정한다.")
    void updateCustomer_IdAndDto_ReturnCustomerResponse() {
        //given
        Customer savedCustomer = customerRepository.save(customer);
        CustomerUpdateRequest request = new CustomerUpdateRequest("Lee", 49, "Lee@naver.com");

        //when
        customerService.updateCustomer(savedCustomer.getId(), request);

        //then
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).get();

        assertThat(updatedCustomer.getCustomerNameValue()).isEqualTo(request.name());
        assertThat(updatedCustomer.getAgeValue()).isEqualTo(request.age());
        assertThat(updatedCustomer.getEmailAddress()).isEqualTo(request.email());
    }

    @Test
    @DisplayName("고객(Customer)를 삭제한다.")
    void deleteCustomer_Id_DeleteSuccess() {
        //given
        Customer savedCustomer = customerRepository.save(customer);

        //when
        customerService.deleteCustomer(savedCustomer.getId());

        //then
        Optional<Customer> optionalCustomer = customerRepository.findById(savedCustomer.getId());

        assertThat(optionalCustomer).isEmpty();
    }

    @Test
    @DisplayName("고객(Customer)를 단건 조회한다.")
    void findCustomerById_Id_ReturnCustomerResponse() {
        //given
        Customer savedCustomer = customerRepository.save(customer);

        //when
        CustomerResponse response = customerService.findCustomerById(savedCustomer.getId());

        //then
        assertThat(response).isNotNull();
        assertThat(response).usingRecursiveComparison().isEqualTo(CustomerResponse.toDto(savedCustomer));
    }

    @ParameterizedTest
    @DisplayName("고객(Customer)를 전체 조회한다.")
    @MethodSource("customerData")
    void findAllCustomers_ReturnCustomerResponses(List<Customer> customers) {
        //given
        customers.forEach(c -> customerRepository.save(c));

        //when
        List<CustomerResponse> responses = customerService.findAllCustomers();

        //then
        List<CustomerResponse> responseExpect = customers.stream()
                .map(CustomerResponse::toDto)
                .collect(Collectors.toList());

        assertThat(responses).hasSize(customers.size());
        assertThat(responses).usingRecursiveComparison().isEqualTo(responseExpect);
    }

    private static Stream<List<Customer>> customerData() {
        Customer customer1 = Customer.builder()
                .customerName(new CustomerName("Kim"))
                .age(new Age(34))
                .email(new Email("kim@naver.com"))
                .build();
        Customer customer2 = Customer.builder()
                .customerName(new CustomerName("park"))
                .age(new Age(35))
                .email(new Email("park@naver.com"))
                .build();
        return Stream.of(List.of(customer1, customer2));
    }
}
