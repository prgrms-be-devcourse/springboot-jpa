package com.example.springboot_jpa.controller;

import com.example.springboot_jpa.entity.Customer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

  private long id;
  private String firstName;
  private String lastName;

  public static List<CustomerDto> translateAll(List<Customer> customers) {
    return customers.stream().map(
        customer -> new CustomerDto(customer.getId(), customer.getFirstName(),
            customer.getLastName())).collect(
        Collectors.toUnmodifiableList());
  }

  public Customer translate() {
    return new Customer(id, firstName, lastName);
  }

}
