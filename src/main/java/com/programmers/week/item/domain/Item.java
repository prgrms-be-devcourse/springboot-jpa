package com.programmers.week.item.domain;

import com.programmers.week.base.BaseEntity;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  private int price;
  private int stockQuantity;

  protected Item() {
  }

  protected Item(int price, int stockQuantity) {
    validatePrice(price);
    validateStockQuantity(stockQuantity);
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  private static void validatePrice(int price) {
    if (price < 0) {
      throw new IllegalArgumentException(String.format("가격이 음수입니다. ===> %s", price));
    }
  }

  private static void validateStockQuantity(int stockQuantity) {
    if (stockQuantity < 0) {
      throw new IllegalArgumentException(String.format("재고가 음수입니다. ===> %s", stockQuantity));
    }
  }


}
