package com.kdt.lecture.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import com.kdt.lecture.exception.SoldOutException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @Positive
  @Column(name = "price", nullable = false)
  private int price;

  @Positive
  @Column(name = "quantity", nullable = false)
  private int quantity;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;

  public OrderItem(Order order, Item item, int quantity) {
    if (item.isSoldOut()) {
      throw new SoldOutException();
    }
    this.item = item;
    this.order = order;
    this.quantity = quantity;
    this.price = quantity * item.getPrice();
    order.addOrderItem(this);
    item.decreaseStockQuantity(quantity);
  }
}