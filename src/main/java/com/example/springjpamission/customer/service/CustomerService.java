package com.example.springjpamission.customer.service;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.customer.domain.CustomerRepository;
import com.example.springjpamission.customer.domain.Name;
import com.example.springjpamission.customer.service.dto.CustomerResponses;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.CustomerResponse;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest) {
        Customer customer = new Customer(
                new Name(saveCustomerRequest.firstName(), saveCustomerRequest.lastName()));
        return new CustomerResponse(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer findCustomer = customerRepository.findById(updateCustomerRequest.id())
                .orElseThrow(() -> new RuntimeException("해당 customer는 존재하지 않습니다."));

        findCustomer.changeName(
                new Name(updateCustomerRequest.firstName(), updateCustomerRequest.lastName()));
        return new CustomerResponse(findCustomer);
    }

    @Transactional
    public CustomerResponses findAll() {
        return CustomerResponses.of(customerRepository.findAll());
    }

    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

}
