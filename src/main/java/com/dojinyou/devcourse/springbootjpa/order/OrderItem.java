package com.dojinyou.devcourse.springbootjpa.order;

import com.dojinyou.devcourse.springbootjpa.common.entity.BaseEntity;
import com.dojinyou.devcourse.springbootjpa.item.Item;

import javax.persistence.*;

@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;

    private long quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    protected OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
        this.price = item.getPrice() * quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.item = builder.item;
        setQuantity(builder.quantity);
    }

    public static class Builder {
        private Long id;
        private Long quantity;
        private Item item;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder quantity(long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder item(Item item) {
            this.item = item;
            return this;
        }

        public OrderItem build() {
            if (quantity == 0 || item == null) {
                throw new IllegalArgumentException();
            }
            return new OrderItem(this);
        }
    }
}
