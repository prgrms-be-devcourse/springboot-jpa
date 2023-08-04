package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.domain.customer.Customer;
import com.programmers.springbootjpa.dto.request.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.request.CustomerUpdateRequest;
import com.programmers.springbootjpa.dto.response.CustomerResponse;
import com.programmers.springbootjpa.repository.CustomerRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public void create(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .name(customerCreateRequest.getName())
                .age(customerCreateRequest.getAge())
                .nickName(customerCreateRequest.getNickName())
                .address(customerCreateRequest.getAddress())
                .build();

        customerRepository.save(customer);
    }

    public List<CustomerResponse> readAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerResponse::new)
                .toList();
    }

    public CustomerResponse readById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("찾는 사용자가 없습니다."));

        return new CustomerResponse(customer);
    }

    @Transactional
    public void updateById(Long id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("업데이트 할 사용자가 없습니다."));

        customer.changeNickName(customerUpdateRequest.getNickName());
        customer.changeAddress(customerUpdateRequest.getAddress());
    }

    @Transactional
    public void deleteById(Long id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("삭제할 사용자가 없습니다."));

        customerRepository.deleteById(id);
    }
}
