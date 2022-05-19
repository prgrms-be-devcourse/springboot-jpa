package com.programmers.springbootjpa.domain.order;

import com.programmers.springbootjpa.domain.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Item() {}

    public Item(int price, int stockQuantity) {
        validatePrice(price);
        validateStockQuantity(stockQuantity);
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /* Data Validation */

    private void validatePrice(int price) {
        Assert.notNull(price, "Price should not be null.");
        Assert.isTrue(price >= 0, "Price should be greater than or equal to 0 ");
    }

    private void validateStockQuantity(int stockQuantity) {
        Assert.notNull(stockQuantity, "Stock quantity should not be null.");
        Assert.isTrue(stockQuantity >= 0, "Stock Quantity should be greater than or equal to 0 ");
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }

    /* getter */

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
