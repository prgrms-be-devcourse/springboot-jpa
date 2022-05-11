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

  public void addOrderItem(OrderItem orderItem) {
    orderItem.setOrder(this);
  }

  public Long getId() {
    return id;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
