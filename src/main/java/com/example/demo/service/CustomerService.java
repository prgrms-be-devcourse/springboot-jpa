package com.example.demo.service;

import com.example.demo.converter.OrderConverter;
import com.example.demo.domain.Customer;
import com.example.demo.dto.CustomerDto;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public long createCustomer(Customer customer) {
        Customer entity = customerRepository.save(customer);
        return entity.getCustomerId();
    }

    public Page<CustomerDto> findAll(Pageable pageable) {
        Page<CustomerDto> result = customerRepository.findAll(pageable).map(OrderConverter::toCustomerDto);
        return result;
    }

    public CustomerDto findById(long customerId) {
        return OrderConverter.toCustomerDto(customerRepository.findById(customerId)
                .orElseThrow(()-> new RuntimeException("There is no customer with ID" + customerId)));
    }

    public String deleteById(long customerId) {
        customerRepository.deleteById(customerId);
        return "Successfully delete customer " + customerId;
    }
}
