package com.example.springboot_jpa.controller;

import com.example.springboot_jpa.repository.CustomerRepository;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private final CustomerRepository repository;

  public CustomerController(CustomerRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/api/v1/customers")
  public Map<String, String> createCustomer(@RequestBody CustomerDto customerDto) {
    repository.save(customerDto.translate());

    return Map.of("result", "success");
  }

  @DeleteMapping("/api/v1/customers")
  public Map<String, String> deleteCustomerById(
      @RequestBody CustomerDto customerDto) {
    repository.deleteById(customerDto.getId());

    return Map.of("result", "success");
  }

  @PutMapping("/api/v1/customers")
  public CustomerDto updatedCustomer(@RequestBody CustomerDto customerDto) {
    repository.save(customerDto.translate());

    return customerDto;
  }

  @GetMapping("/api/v1/customers")
  public List<CustomerDto> getAllCustomers() {

    return CustomerDto.translateAll(repository.findAll());
  }
}
