package com.prgrms.springbootjpa.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {
  @Id
  @Column(name = "id")
  private Long id;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }
}
