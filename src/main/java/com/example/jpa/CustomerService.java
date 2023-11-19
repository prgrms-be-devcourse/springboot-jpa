package com.example.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    public List<CustomerResponseDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }

    public Long saveCustomer(CustomerSaveRequestDto requestDto) {
        Customer customer = Customer.of(requestDto);
        return customerRepository.save(customer).getId();
    }

    @Transactional
    public Long updateCustomer(CustomerUpdateRequestDto requestDto, Long customerId) {
        Customer findCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        findCustomer.updateInfo(requestDto);
        return findCustomer.getId();
    }

    @Transactional
    public Long deleteCustomer(Long customerId) {
        Customer findCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        customerRepository.delete(findCustomer);
        return findCustomer.getId();
    }
}
