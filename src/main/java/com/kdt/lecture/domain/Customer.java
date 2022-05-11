package com.kdt.lecture.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

  @Id
  private Long id;

  @NotBlank @Size(min = 2, max = 20)
  @Column(name = "first_name", length = 20, nullable = false)
  private String firstName;

  @NotBlank @Size(min = 2, max = 20)
  @Column(name = "last_name", length = 20, nullable = false)
  private String lastName;

  public void updateFullName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}