package com.programmers.week.item.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {

  private long power;

  protected Car() {
  }

  private Car(int price, int stockQuantity, long power) {
    super(price, stockQuantity);
    this.power = power;
  }

  public static Item of(int price, int stockQuantity, long power){
    validatePower(power);
    return new Car(price, stockQuantity, power);
  }

  private static void validatePower(long power) {
    if (power < 0) {
      throw new IllegalStateException(String.format("요리사가 비어있습니다. ==> %s", power));
    }
  }

}
