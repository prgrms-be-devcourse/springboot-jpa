package com.example.demo.controller;

import com.example.demo.converter.OrderConverter;
import com.example.demo.dto.CustomerDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Long> createCustomer(@RequestBody CustomerDto customerDto) {
        Long id = customerService.createCustomer(OrderConverter.toCustomer(customerDto));
        return ApiResponse.ok(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Page<CustomerDto>> getAllCustomer(Pageable pageable) {
        Page<CustomerDto> result = customerService.findAll(pageable);
        return ApiResponse.ok(result);
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CustomerDto> getCustomer(@PathVariable long customerId) {
        CustomerDto result = customerService.findById(customerId);
        return ApiResponse.ok(result);
    }

    @DeleteMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteCustomer(@PathVariable long customerId) {
        String result = customerService.deleteById(customerId);
        return ApiResponse.ok(result);
    }
}
