package com.example.springboot_jpa.controller;

import com.example.springboot_jpa.entity.Customer;
import lombok.Data;

@Data
public class CustomerRequest {

  private long id;
  private String firstName;
  private String lastName;

  public Customer translate() {
    return new Customer(id, firstName, lastName);
  }

}
