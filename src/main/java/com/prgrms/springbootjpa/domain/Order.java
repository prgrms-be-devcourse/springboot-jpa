package com.prgrms.springbootjpa.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order")
public class Order {
  @Id
  @Column(name = "id")
  private Long id;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }
}
