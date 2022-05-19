package com.example.springjpa.domain.order;

import com.example.springjpa.exception.NoStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int stockQuantity;

    private static final String NO_STOCK_ERR_MSG = "아이템의 수량이 부족합니다. 현재 수랑: ";
    private static final int ZERO_STOCK = 0;
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    protected Item() {
    }

    public Item(Long id, int price, int stockQuantity, List<OrderItem> orderItems) {
        this.id = id;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.orderItems = orderItems == null ? new ArrayList<>() : orderItems;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    private void validate() {
        if (stockQuantity <= ZERO_STOCK)
            throw new NoStockException(NO_STOCK_ERR_MSG + this.stockQuantity);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }

    public void consume() {
        validate();
        this.stockQuantity--;
    }

    public static class ItemBuilder {
        private Long id;
        private int price;
        private int stockQuantity;
        private List<OrderItem> orderItems;

        ItemBuilder() {
        }

        public ItemBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public ItemBuilder price(final int price) {
            this.price = price;
            return this;
        }

        public ItemBuilder stockQuantity(final int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public ItemBuilder orderItems(final List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Item build() {
            return new Item(this.id, this.price, this.stockQuantity, this.orderItems);
        }

        public String toString() {
            return "ItemBuilder(id=" + this.id + ", price=" + this.price + ", stockQuantity=" + this.stockQuantity + ", orderItems=" + this.orderItems + ")";
        }
    }
}
