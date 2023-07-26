package com.programmers.springbootjpa.controller;

import com.programmers.springbootjpa.dto.request.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.request.CustomerUpdateRequest;
import com.programmers.springbootjpa.dto.response.CustomerResponse;
import com.programmers.springbootjpa.service.CustomerService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/customers")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> readCustomer(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.readCustomer(id);

        return ResponseEntity.ok(customerResponse);
    }
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> readAllCustomer() {
        List<CustomerResponse> customerResponses = customerService.readAllCustomer();

        return ResponseEntity.ok(customerResponses);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerUpdateRequest);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}
