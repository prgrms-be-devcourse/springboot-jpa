package com.example.springboot_jpa.controller;

import com.example.springboot_jpa.entity.Customer;
import com.example.springboot_jpa.repository.CustomerRepository;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private final CustomerRepository repository;

  public CustomerController(CustomerRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/api/v1/customers")
  public Map<String, String> createCustomer(@RequestBody Customer customer) {
    repository.save(customer);

    return Map.of("result", "created");
  }
}
