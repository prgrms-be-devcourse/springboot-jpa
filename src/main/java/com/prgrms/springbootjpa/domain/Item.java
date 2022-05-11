package com.prgrms.springbootjpa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {
  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_item_id", referencedColumnName = "id")
  private OrderItem orderItem;

  public Long getId() {
    return id;
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setOrderItem(OrderItem orderItem) {
    if (Objects.nonNull(this.orderItem)) {
      this.orderItem.getItems().remove(this);
    }
    this.orderItem = orderItem;
    orderItem.getItems().add(this);
  }
}
