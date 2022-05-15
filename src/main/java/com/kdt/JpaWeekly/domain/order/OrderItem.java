package com.kdt.JpaWeekly.domain.order;

import com.kdt.JpaWeekly.common.domain.BaseEntity;
import com.kdt.JpaWeekly.domain.item.Item;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {
        super();
    }

    public OrderItem(Builder builder) {
        super(builder.createdBy, builder.createdAt);
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.order = builder.order;
        this.item = builder.item;
    }

    public static class Builder {
        private int price;
        private int quantity;
        private Order order;
        private Item item;
        private String createdBy;
        private LocalDateTime createdAt;

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder item(Item item) {
            this.item = item;
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

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
