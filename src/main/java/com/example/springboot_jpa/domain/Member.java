package com.example.springboot_jpa.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @Column(nullable = false, length = 30, unique = true)
  private String nickName;

  private int age;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "description", nullable = true)
  private String description;

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  public void addOrder(Order order) {
    order.setMember(this);
  }
}
