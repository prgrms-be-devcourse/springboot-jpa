package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.domain.Customer;
import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.CustomerResponse;
import com.programmers.springbootjpa.dto.CustomerUpdateRequest;
import com.programmers.springbootjpa.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        Customer savedCustomer = customerRepository.save(request.toEntity());

        return CustomerResponse.of(savedCustomer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerResponse::of).toList();
    }

    @Transactional
    public CustomerResponse updateCustomerById(Long id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 구매자가 없습니다."));

        customer.updateName(request.getName());
        customer.updateAge(request.getAge());
        customer.updateAddress(request.getAddress());

        return CustomerResponse.of(customer);
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID에 해당하는 구매자가 없습니다.");
        }

        customerRepository.deleteById(id);
    }
}
