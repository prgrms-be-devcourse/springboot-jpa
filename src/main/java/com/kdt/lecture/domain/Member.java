package com.kdt.lecture.domain;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @NotNull
  @Size(max = 30)
  @Column(name = "name")
  private String name;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(unique = true)
  private String nickName;

  @Positive
  @Column(name = "age")
  private int age;

  @NotNull
  @Size(min = 2, max = 50)
  @Column(name = "address", length = 50)
  private String address;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "member")
  private final List<Order> orders = new ArrayList<>();

  public void addOrder(Order order) {
    if(orders.contains(order)) return;
    orders.add(order);
  }

  public Member(String name, String nickName, int age, String address) {
    this.name = name;
    this.nickName = nickName;
    this.age = age;
    this.address = address;
  }
}