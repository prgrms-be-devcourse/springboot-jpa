package com.kdt.JpaWeekly.domain.item;

import com.kdt.JpaWeekly.domain.order.Order;
import com.kdt.JpaWeekly.domain.order.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {
    private int power;

    protected Car() {
        super();
    }

    private Car(Builder builder) {
        super(builder.createdBy, builder.createdAt, builder.price, builder.stockQuantity);
        this.power = builder.power;
    }

    public static class Builder {
        private int price;
        private int stockQuantity;
        private int power;
        private String createdBy;
        private LocalDateTime createdAt;

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
