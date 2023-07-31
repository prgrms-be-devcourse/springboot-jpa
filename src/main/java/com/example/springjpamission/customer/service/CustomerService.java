package com.example.springjpamission.customer.service;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.customer.domain.CustomerRepository;
import com.example.springjpamission.customer.domain.Name;
import com.example.springjpamission.customer.service.dto.CustomerRespones;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.CustomerRespone;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerRespone saveCustomer(SaveCustomerRequest saveCustomerRequest) {
        Customer customer = new Customer(
                new Name(saveCustomerRequest.firstName(), saveCustomerRequest.lastName()));
        return new CustomerRespone(customerRepository.save(customer));
    }

    @Transactional
    public CustomerRespone updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer findCustomer = customerRepository.findById(updateCustomerRequest.id())
                .orElseThrow(() -> new RuntimeException("해당 customer는 존재하지 않습니다."));

        findCustomer.changeName(
                new Name(updateCustomerRequest.firstName(), updateCustomerRequest.lastName()));
        return new CustomerRespone(findCustomer);
    }

    public CustomerRespones findAll() {
        return CustomerRespones.of(customerRepository.findAll());
    }

    @Transactional
    public void deletById(Long id) {
        customerRepository.deleteById(id);
    }

}
