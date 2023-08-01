package com.jpaweekily.domain.customer.application;

import com.jpaweekily.domain.customer.Customer;
import com.jpaweekily.domain.customer.CustomerMapper;
import com.jpaweekily.domain.customer.dto.CustomerRequest;
import com.jpaweekily.domain.customer.dto.CustomerResponse;
import com.jpaweekily.domain.customer.dto.CustomerUpdate;
import com.jpaweekily.domain.customer.infrastructrue.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return customerRepository.save(customer).getId();
    }

    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return CustomerMapper.convertEntityToResponse(customer);
    }

    public Page<CustomerResponse> findCustomersWithPaging(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerMapper::convertEntityToResponse);
    }

    @Transactional
    public void update(Long id, CustomerUpdate request) {
        Customer customer = customerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        customer.update(request.firstName(), request.lastName());
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
