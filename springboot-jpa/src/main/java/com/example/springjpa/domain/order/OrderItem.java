package com.example.springjpa.domain.order;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(Long id, int price, int quantity, Order order, Item item) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        item.consume();
        if (Objects.nonNull(this.item)) {
            this.item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }

    public static class OrderItemBuilder {
        private Long id;
        private int price;
        private int quantity;
        private Order order;
        private Item item;

        OrderItemBuilder() {
        }

        public OrderItemBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder price(final int price) {
            this.price = price;
            return this;
        }

        public OrderItemBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder order(final Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder item(final Item item) {
            this.item = item;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this.id, this.price, this.quantity, this.order, this.item);
        }

        public String toString() {
            return "OrderItemBuilder(id=" + this.id + ", price=" + this.price + ", quantity=" + this.quantity + ")";
        }
    }
}
