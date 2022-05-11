package com.prgrms.springbootjpa.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {
  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @OneToMany(mappedBy = "orderItem")
  private List<Item> items;

  public void addItem(Item item) {
    item.setOrderItem(this);
    items.add(item);
  }

  public void setOrder(Order order) {
    if (Objects.nonNull(this.order)) {
      this.order.getOrderItems().remove(this);
    }
    this.order = order;
    order.getOrderItems().add(this);
  }

  public List<Item> getItems() {
    return items;
  }
}
