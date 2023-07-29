package com.programmers.jpa.customer.ui;


import com.programmers.jpa.customer.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
