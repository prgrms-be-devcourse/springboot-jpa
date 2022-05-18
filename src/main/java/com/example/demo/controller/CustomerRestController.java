package com.example.demo.controller;

import com.example.demo.converter.CustomerConverter;
import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Long id = customerService.createCustomer(CustomerConverter.toCustomer(customerDto));
        return id;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCustomer(@PathVariable long id) {
        CustomerDto result = customerService.findById(id);
        return result;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCustomer(@PathVariable long id) {
        String result = customerService.deleteById(id);
        return result;
    }
}
