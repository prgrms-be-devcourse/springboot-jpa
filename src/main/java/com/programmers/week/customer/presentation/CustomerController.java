package com.programmers.week.customer.presentation;

import com.programmers.week.customer.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@RequestBody CustomerCreateRequest request) {
        return customerService.create(request);
    }

    @ResponseStatus
    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @ResponseStatus
    @GetMapping
    public Page<CustomerResponse> findAll(@RequestBody PageRequestDto request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.size());
        return customerService.findAll(pageRequest);
    }

    @ResponseStatus
    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        return customerService.update(id, request);
    }

    @ResponseStatus
    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }

}
