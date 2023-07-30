package com.blackdog.springbootjpa.domain.customer.service;

import com.blackdog.springbootjpa.domain.customer.dto.CustomerUpdateRequest;
import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.domain.customer.dto.CustomerCreateRequest;
import com.blackdog.springbootjpa.domain.customer.dto.CustomerResponse;
import com.blackdog.springbootjpa.domain.customer.repository.CustomerRepository;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.Name;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerConverter customerConverter;

    /**
     * 고객 생성
     * @param customerCreateRequest
     * @return CustomerResponse
     */
    @Transactional
    public CustomerResponse createCustomer(@Valid CustomerCreateRequest customerCreateRequest){
        Customer customer = customerConverter.toEntity(customerCreateRequest);

        Customer savedCustomer = repository.save(customer);
        return CustomerResponse.toDto(savedCustomer);
    }

    /**
     * 고객 수정
     * @param id
     * @param customerUpdateRequest
     * @return CustomerResponse
     */
    @Transactional
    public CustomerResponse updateCustomer(int id, @Valid CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 고객이 없습니다"));

        customer.changeCustomer(CustomerDto.toDto(customerUpdateRequest));

        return CustomerResponse.toDto(customer);
    }

    /**
     * 고객 삭제
     * @param id
     */
    @Transactional
    public void deleteCustomer(int id){
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 고객이 없습니다"));

        repository.delete(customer);
    }

    /**
     * 고객 단일 조회
     * @param id
     * @return CustomerResponse
     */
    @Transactional
    public CustomerResponse findCustomerById(int id){ //todo : 네이밍 고민!
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 고객이 없습니다"));

        return CustomerResponse.toDto(customer);
    }

    /**
     * 고객 전체 조회
     * @return List<CustomerResponse>
     */
    @Transactional
    public List<CustomerResponse> findAllCustomers(){
        List<Customer> customers = repository.findAll();

        return customers.stream()
                .map(CustomerResponse::toDto)
                .collect(Collectors.toList());
    }

}
