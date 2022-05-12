package com.example.springboot_jpa.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private int price;
  private int stockQuantity;

  @ManyToOne
  @JoinColumn(name = "order_item_id", referencedColumnName = "id")
  private OrderItem orderItem;

  public void setOrderItem(OrderItem orderItem) {
    if (Objects.nonNull(this.orderItem)) {
      this.orderItem.getItems().remove(this);
    }

    this.orderItem = orderItem;
    orderItem.getItems().add(this);
  }
}
