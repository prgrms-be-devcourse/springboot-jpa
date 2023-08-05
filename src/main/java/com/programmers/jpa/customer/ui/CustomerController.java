package com.programmers.jpa.customer.ui;


import com.programmers.jpa.customer.application.CustomerService;
import com.programmers.jpa.customer.dto.CreateRequest;
import com.programmers.jpa.customer.dto.FindResponse;
import com.programmers.jpa.customer.dto.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    public Long createCustomer(@RequestBody CreateRequest createRequest) {
        return customerService.create(createRequest);
    }

    @GetMapping("/customers/{id}")
    public FindResponse findCustomer(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/customers")
    public Page<FindResponse> findCustomers(Pageable pageable) {
        return customerService.findAll(pageable);
    }

    @PatchMapping("/customers")
    public Long updateCustomer(@RequestBody UpdateRequest updateRequest) {
        return customerService.update(updateRequest);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
