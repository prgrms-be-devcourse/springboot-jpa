package com.example.jpaweekly.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @NotBlank
  @Column(nullable = false, length = 30, unique = true)
  private String nickName;

  @Column
  @Size(min = 1)
  private int age;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  @Builder
  public Member(String name, String nickName, Integer age, String address, String description) {
    this.name = name;
    this.nickName = nickName;
    this.age = age;
    this.address = address;
    this.description = description;
  }

  public void addOrder(Order order) {
    orders.add(order);
  }
}
