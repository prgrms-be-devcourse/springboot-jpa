package com.example.jpa.customer;

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
        Customer findCustomer = findCustomer(customerId);
        return findCustomer.updateInfo(requestDto);
    }

    @Transactional
    public Long deleteCustomer(Long customerId) {
        Customer findCustomer = findCustomer(customerId);
        customerRepository.delete(findCustomer);
        return findCustomer.getId();
    }

    public CustomerResponseDto getCustomer(Long customerId) {
        return CustomerResponseDto.of(findCustomer(customerId));
    }

    private Customer findCustomer(Long customerId) {
        Customer findCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        return findCustomer;
    }
}
