package org.programmers.jpaweeklymission.customer.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.programmers.jpaweeklymission.customer.application.CustomerService;
import org.programmers.jpaweeklymission.customer.application.dto.CustomerResponse;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerCreationRequest;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")  // TODO: 근데 이거 "/api" 쓰는 이유가 있나? 다 api 아닌가?
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
