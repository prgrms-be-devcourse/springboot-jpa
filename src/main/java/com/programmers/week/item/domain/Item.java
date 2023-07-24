package com.programmers.week.item.domain;

import com.programmers.week.base.BaseEntity;
import com.programmers.week.exception.Message;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  private int price;
  private int stockQuantity;

  protected Item(int price, int stockQuantity) {
    validatePrice(price);
    validateStockQuantity(stockQuantity);
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  private static void validatePrice(int price) {
    if (price < 0) {
      throw new IllegalArgumentException(String.format(Message.TOTAL_PRICE_IS_MINUS + "%s", price));
    }
  }

  private static void validateStockQuantity(int stockQuantity) {
    if (stockQuantity < 0) {
      throw new IllegalArgumentException(String.format(Message.TOTAL_QUANTITY_IS_MINUS + "%s", stockQuantity));
    }
  }

}
