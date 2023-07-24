package com.programmers.week.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Customer {

  @Id
  @GeneratedValue
  private Long id;

  @Column(length = 5, nullable = false)
  private String firstName;

  @Column(length = 2, nullable = false)
  private String lastName;

  protected Customer() {
  }

  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

}
