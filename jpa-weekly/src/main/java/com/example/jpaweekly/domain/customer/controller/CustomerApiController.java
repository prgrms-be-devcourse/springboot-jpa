package com.example.jpaweekly.domain.customer.controller;

import com.example.jpaweekly.domain.customer.dto.CustomerRequest;
import com.example.jpaweekly.domain.customer.dto.CustomerResponse;
import com.example.jpaweekly.domain.customer.dto.CustomerUpdate;
import com.example.jpaweekly.domain.customer.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerApiController {

    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> customerList() {
        List<CustomerResponse> customers = customerService.findCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CustomerResponse>> customerPage(@PageableDefault() Pageable pageable) {
        Page<CustomerResponse> customers = customerService.findCustomersWithPaging(pageable);
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/new")
    public ResponseEntity<Long> customerCreate(@RequestBody CustomerRequest request) {
        Long id = customerService.create(request);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<CustomerResponse> customerUpdate(@RequestBody CustomerUpdate request) {
        CustomerResponse updated = customerService.update(request);
        return ResponseEntity.ok(updated);
    }

}
