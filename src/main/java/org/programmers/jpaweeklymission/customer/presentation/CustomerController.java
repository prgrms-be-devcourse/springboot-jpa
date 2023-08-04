package org.programmers.jpaweeklymission.customer.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.programmers.jpaweeklymission.customer.application.CustomerService;
import org.programmers.jpaweeklymission.customer.application.dto.CustomerResponse;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerCreationRequest;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerCreationRequest request) {
        return customerService.saveCustomer(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerUpdateRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
