package com.programmers.springbootjpa.domain.order;

import com.programmers.springbootjpa.domain.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {}

    public OrderItem(int price, int quantity) {
        validatePrice(price);
        validateQuantity(quantity);
        this.price = price;
        this.quantity = quantity;
    }

    /* Data Validation */

    private void validatePrice(int price) {
        Assert.notNull(price, "Price should not be null.");
        Assert.isTrue(price >= 0, "Price should be greater than or equal to 0 ");
    }

    private void validateQuantity(int quantity) {
        Assert.notNull(quantity, "Quantity should not be null.");
        Assert.isTrue(quantity >= 0, "Quantity should be greater than or equal to 0 ");
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if (Objects.nonNull(this.item)) {
            this.item.getOrderItems().remove(this);
        }

        this.item = item;
        item.getOrderItems().add(this);
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

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }
}
