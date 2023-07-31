package com.programmers.springbootjpa.controller;

import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.CustomerResponse;
import com.programmers.springbootjpa.dto.CustomerUpdateRequest;
import com.programmers.springbootjpa.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CustomerCreateRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping
    public List<CustomerResponse> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomerById(@PathVariable Long id,
            @RequestBody CustomerUpdateRequest request) {
        return customerService.updateCustomerById(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
