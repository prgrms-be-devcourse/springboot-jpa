package com.example.jpaweekly.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @Size(max = 100000000)
  private int price;

  @Column
  @Size(max = 1000)
  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  private Item item;

  public void updateOrder(Order order) {
    if (Objects.nonNull(this.order)) {
      this.order.getOrderItems().remove(this);
    }

    this.order = order;
    order.getOrderItems().add(this);
  }

  public void updateItem(Item item) {
    if (Objects.nonNull(this.item)) {
      this.item.getOrderItem().remove(this);
    }

    this.item = item;
    item.getOrderItem().add(this);
  }
}