package com.kdt.lecture.domain;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import com.kdt.lecture.exception.SoldOutException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item")
@NoArgsConstructor(access = PROTECTED)
public class Item {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @Positive
  @Column(name = "price")
  private int price;

  @PositiveOrZero
  @Column(name = "stock_quantity")
  private int stockQuantity;

  public Item(int price, int stockQuantity) {
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public void decreaseStockQuantity(int quantity) {
    if (stockQuantity - quantity < 0) {
      throw new SoldOutException();
    }
    stockQuantity -= quantity;
  }

  public boolean isSoldOut() {
    return stockQuantity <= 0;
  }
}