package com.kdt.JpaWeekly.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {
    private int width;
    private int height;

    protected Furniture() {
        super();
    }

    private Furniture(Builder builder) {
        super(builder.createdBy, builder.createdAt, builder.price, builder.stockQuantity);
        this.width = width;
        this.height = height;
    }

    public static class Builder {
        private int price;
        private int stockQuantity;
        private int width;
        private int height;
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

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
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

        public Furniture build() {
            return new Furniture(this);
        }
    }
}
