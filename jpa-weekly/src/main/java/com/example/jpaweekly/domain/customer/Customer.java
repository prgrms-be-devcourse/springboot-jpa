package com.example.jpaweekly.domain.customer;
import jakarta.persistence.*;
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
  private long id;

  @Column(nullable = false, length = 30)
  private String firstName;

  @Column(nullable = false, length = 30)
  private String lastName;

  public void updateFirstName(String inputFirstName) {
    this.firstName = inputFirstName;
  }

  public void updateLastName(String inputLastName) {
    this.lastName = inputLastName;
  }
}
