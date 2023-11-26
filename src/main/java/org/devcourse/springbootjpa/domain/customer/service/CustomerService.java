package org.devcourse.springbootjpa.domain.customer.service;

import org.devcourse.springbootjpa.domain.customer.Customer;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerInsertDto;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerMapper;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerResponseDto;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerUpdateDto;
import org.devcourse.springbootjpa.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    public CustomerResponseDto insert(CustomerInsertDto customerInsertDto) {
        Customer customer = mapper.fromInsertDto(customerInsertDto);
        Customer savedCustomer = customerRepository.save(customer);

        return mapper.toResponseDto(savedCustomer);
    }

    public CustomerResponseDto update(CustomerUpdateDto customerUpdateDto) {
        Customer customer = mapper.fromUpdateDto(customerUpdateDto);

        customerRepository.findById(customer.getId())
                .orElseThrow(EntityNotFoundException::new);

        Customer updatedCustomer = customerRepository.save(customer);

        return mapper.toResponseDto(updatedCustomer);
    }

    public List<CustomerResponseDto> findAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(mapper::toResponseDto).toList();
    }

    public CustomerResponseDto findById(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapper.toResponseDto(customer);
    }

    public boolean deleteById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            return false;
        }

        customerRepository.deleteById(id);

        return true;
    }
}
