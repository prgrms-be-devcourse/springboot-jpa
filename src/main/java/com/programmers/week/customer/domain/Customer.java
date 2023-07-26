package com.programmers.week.customer.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

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
    validateFirstName(firstName);
    validateLastName(lastName);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void changeFirstName(String firstName) {
    validateFirstName(firstName);
    this.firstName = firstName;
  }

  private static void validateFirstName(String firstName) {
    if (Objects.isNull(firstName) || firstName.isBlank()) {
      throw new IllegalStateException("이름이 비어있습니다.");
    }
  }

  public void changeLastName(String lastName) {
    validateLastName(lastName);
    this.lastName = lastName;
  }

  private static void validateLastName(String lastName) {
    if (Objects.isNull(lastName) || lastName.isBlank()) {
      throw new IllegalStateException("성이 비어있습니다.");
    }
  }

}
