package org.devcourse.springbootjpa.domain.customer.controller;

import org.devcourse.springbootjpa.domain.customer.dto.CustomerInsertRequestDto;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerMapper;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerResponseDto;
import org.devcourse.springbootjpa.domain.customer.dto.CustomerUpdateRequestDto;
import org.devcourse.springbootjpa.domain.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    public CustomerRestController(CustomerService customerService, CustomerMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponseDto> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto insert(@RequestBody CustomerInsertRequestDto insertRequestDto) {
        return customerService.insert(mapper.insertRequestDtoToInsertDto(insertRequestDto));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto update(@RequestBody CustomerUpdateRequestDto updateRequestDto) {
        return customerService.insert(mapper.updateRequestDtoToInsertDto(updateRequestDto));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam("id") long id) {
        customerService.deleteById(id);
    }
}
