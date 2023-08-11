package com.example.springjpamission.customer.service;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.customer.domain.CustomerRepository;
import com.example.springjpamission.customer.domain.Name;
import com.example.springjpamission.customer.service.dto.CustomerResponses;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.CustomerResponse;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest) {
        Customer customer = new Customer(
                new Name(saveCustomerRequest.firstName(), saveCustomerRequest.lastName()));
        return CustomerResponse.of(customerRepository.save(customer));
    }

    public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer findCustomer = customerRepository.findById(updateCustomerRequest.id())
                .orElseThrow(() ->  new EntityNotFoundException("해당 customer는 존재하지 않습니다."));

        findCustomer.changeName(
                new Name(updateCustomerRequest.firstName(), updateCustomerRequest.lastName()));
        return CustomerResponse.of(findCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponses findAll(Pageable pageable) {
        return CustomerResponses.of(customerRepository.findAll(pageable));
    }

    public void deleteById(Long id) {
        customerRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("해당 customer는 존재하지 않습니다."));

        customerRepository.deleteById(id);
    }

}
