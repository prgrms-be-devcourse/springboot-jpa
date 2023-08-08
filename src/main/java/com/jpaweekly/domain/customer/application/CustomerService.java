package com.jpaweekly.domain.customer.application;

import com.jpaweekly.domain.customer.dto.CustomerRequest;
import com.jpaweekly.domain.customer.dto.CustomerResponse;
import com.jpaweekly.domain.customer.dto.CustomerUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Long create(CustomerRequest request);

    CustomerResponse findCustomerById(Long id);

    Page<CustomerResponse> findCustomersWithPaging(Pageable pageable);

    void update(Long id, CustomerUpdate request);

    void delete(Long id);

}
