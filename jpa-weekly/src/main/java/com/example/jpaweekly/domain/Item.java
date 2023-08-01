package com.example.jpaweekly.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private int price;
  @Column
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