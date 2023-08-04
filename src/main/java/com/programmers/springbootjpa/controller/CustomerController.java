package com.programmers.springbootjpa.controller;

import com.programmers.springbootjpa.dto.request.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.request.CustomerUpdateRequest;
import com.programmers.springbootjpa.dto.response.CustomerResponse;
import com.programmers.springbootjpa.service.CustomerService;
import jakarta.validation.Valid;
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


@RequestMapping("/api/customers")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        customerService.create(customerCreateRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse readById(@PathVariable Long id) {
        return customerService.readById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> readAll() {
        return customerService.readAll();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateById(id, customerUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
    }
}
