package com.example.demo.service;

import com.example.demo.converter.CustomerConverter;
import com.example.demo.domain.Customer;
import com.example.demo.dto.CustomerDto;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public long createCustomer(Customer customer) {
        Customer entity = customerRepository.save(customer);
        return entity.getCustomerId();
    }

    public CustomerDto findById(long customerId) {
        return CustomerConverter.toCustomerDto(customerRepository.findById(customerId)
                .orElseThrow(()-> new RuntimeException("There is no customer with ID " + customerId)));
    }

    @Transactional
    public String deleteById(long customerId) {
        customerRepository.deleteById(customerId);
        return "Successfully delete customer " + customerId;
    }
}
