package com.programmers.springbootjpa.controller;

import com.programmers.springbootjpa.domain.Customer;
import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.CustomerResponse;
import com.programmers.springbootjpa.service.CustomerService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CustomerCreateRequest request) {
        return customerService.createCustomer(request);

    }

    @GetMapping
    public List<CustomerResponse> findAllCustomers(){
        return customerService.findAllCustomers();
    }
}
