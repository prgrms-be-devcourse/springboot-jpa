package com.kdt.lecture.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
  private String firstName;
  private String lastName;

  public void updateFullName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}