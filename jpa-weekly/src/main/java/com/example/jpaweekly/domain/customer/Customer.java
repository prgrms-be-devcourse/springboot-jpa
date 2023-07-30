package com.example.jpaweekly.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  @Pattern(regexp = "^[a-zA-Z가-힣]*$")
  @Size(max = 30)
  @NotBlank
  private String firstName;

  @Column(nullable = false, length = 30)
  @Pattern(regexp = "^[a-zA-Z가-힣]*$")
  @Size(max = 30)
  @NotBlank
  private String lastName;

  public void updateFirstName(String inputFirstName) {
    this.firstName = inputFirstName;
  }

  public void updateLastName(String inputLastName) {
    this.lastName = inputLastName;
  }

  @Builder
  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
