package com.kdt.JpaWeekly.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

    protected Food() {
        super();
    }

    private Food(Builder builder) {
        super(builder.createdBy, builder.createdAt, builder.price, builder.stockQuantity);
        this.chef = builder.chef;
    }

    public static class Builder {
        private int price;
        private int stockQuantity;
        private String chef;
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

        public Builder chef(String chef) {
            this.chef = chef;
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

        public Food build() {
            return new Food(this);
        }
    }
}
