package com.programmers.week.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.Objects;

@Entity
@DiscriminatorValue("FOOD")
@Getter
public class Food extends Item {

  @Column(length = 5)
  private String chef;

  protected Food() {

  }

  private Food(int price, int stockQuantity, String chef) {
    super(price, stockQuantity);
    this.chef = chef;
  }

  public static Item of(int price, int stockQuantity, String chef) {
    validateFood(chef);
    return new Food(price, stockQuantity, chef);
  }

  private static void validateFood(String chef) {
    if (Objects.isNull(chef) || chef.isBlank()) {
      throw new IllegalStateException("요리사가 비어있습니다.");
    }
  }

}
