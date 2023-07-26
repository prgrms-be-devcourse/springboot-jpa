package com.programmers.week.orderItem.domain;

import com.programmers.week.base.BaseEntity;
import com.programmers.week.item.domain.Item;
import com.programmers.week.order.domain.Order;
import jakarta.persistence.*;

@Entity
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  protected OrderItem() {

  }

  public OrderItem(Order order, Item item) {
    this.order = order;
    this.item = item;
  }

}
