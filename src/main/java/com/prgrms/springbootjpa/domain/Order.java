package com.prgrms.springbootjpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @Column(name = "id")
  private Long id;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
