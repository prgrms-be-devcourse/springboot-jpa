package com.jpaweekily.domain.customer.service;

import com.jpaweekily.domain.customer.dto.CustomerRequest;
import com.jpaweekily.domain.customer.dto.CustomerResponse;
import com.jpaweekily.domain.customer.dto.CustomerUpdate;

import java.util.List;

public interface CustomerService {

    Long create(CustomerRequest request);

    CustomerResponse findCustomerById(Long id);

    List<CustomerResponse> findCustomers();

    CustomerResponse update(CustomerUpdate request);

    void delete(Long id);

}
