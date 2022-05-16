package com.dojinyou.devcourse.springbootjpa.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Furniture")
public class Furniture extends Item {

    private long width;

    private long height;

    protected Furniture() {
        super();
    }

    public Furniture(int price, int stockQuantity, long width, long height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }

    private Furniture(Builder builder) {
        super(builder.price, builder.stockQuantity);
        this.width = builder.width;
        this.height = builder.height;
    }

    public static class Builder {

        private Long id;

        private Long price;

        private Long stockQuantity;

        private long width;

        private long height;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(long stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Item builder() {
            if (price == null || stockQuantity == null) {
                throw new IllegalArgumentException();
            }
            return new Furniture(this);
        }
    }
}
