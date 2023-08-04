package com.example.jpaweekly.domain.customer.service;

import com.example.jpaweekly.domain.customer.Customer;
import com.example.jpaweekly.domain.customer.CustomerMapper;
import com.example.jpaweekly.domain.customer.dto.CustomerRequest;
import com.example.jpaweekly.domain.customer.dto.CustomerResponse;
import com.example.jpaweekly.domain.customer.dto.CustomerUpdate;
import com.example.jpaweekly.domain.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Long create(CustomerRequest request) {
        Customer customer = CustomerMapper.convertRequestToEntity(request);

        customerRepository.save(customer);
        return customer.getId();
    }

    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(IllegalArgumentException::new);

//        CustomerResponse customerResponse = new CustomerResponse(customer.getFirstName(), customer.getLastName());
//        CustomerResponse from = Customer.from(customer);

        return CustomerMapper.convertEntityToResponse(customer);
    }

    public List<CustomerResponse> findCustomers() {
        return customerRepository.findAll().stream()
                .map(v -> CustomerMapper.convertEntityToResponse(v))
                .toList();
//               .map(CustomerMapper::convertEntityToResponse);
    }

    public Page<CustomerResponse> findCustomersWithPaging(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerMapper::convertEntityToResponse);
    }

    @Transactional
    public CustomerResponse update(CustomerUpdate request) {
        Customer customer = customerRepository.findById(request.id()).orElseThrow(IllegalArgumentException::new);
        customer.changeName(request.firstName(), request.lastName());

        return CustomerMapper.convertEntityToResponse(customer);
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

}
