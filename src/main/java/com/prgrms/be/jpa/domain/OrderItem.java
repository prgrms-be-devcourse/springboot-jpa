package com.prgrms.be.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "total_price")
    private int price;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    public OrderItem(int price, int quantity, Order order, Item item) {
        this.price = price;
        this.quantity = quantity;
        setOrder(order);
        setItem(item);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        this.order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if (Objects.nonNull(this.item)) {
            this.item.getOrderItems().remove(this);
        }

        this.item = item;
        this.item.getOrderItems().add(this);
    }
}
