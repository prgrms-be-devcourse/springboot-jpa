package org.programmers.jpaweeklymission.customer.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerCreationRequest;
import org.programmers.jpaweeklymission.customer.Customer;
import org.programmers.jpaweeklymission.customer.Infra.CustomerJpaRepository;
import org.programmers.jpaweeklymission.customer.application.dto.CustomerResponse;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final String CUSTOMER_NOT_FOUND = "Customer not found for that id: %s";
    private final CustomerJpaRepository customerJpaRepository;

    @Transactional
    public CustomerResponse saveCustomer(CustomerCreationRequest request) {
        Customer customer = CustomerMapper.toCustomer(request);
        Customer saved = customerJpaRepository.save(customer);
        return CustomerMapper.toResponse(saved);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        Customer customer = customerJpaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND, id))
        );
        customer.changeFirstName(request.firstName());
        customer.changeLastName(request.lastName());
        return CustomerMapper.toResponse(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerJpaRepository.deleteById(id);
    }

    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));
        return CustomerMapper.toResponse(customer);
    }
}
