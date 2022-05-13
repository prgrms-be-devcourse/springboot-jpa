package com.example.springbootjpa.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    //fk
    @Column(name = "order_id", insertable = false, updatable = false)
    private String orderId;
    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public OrderItem(int price, OrderStatus orderStatus, Order order, Item item) {
        this.price = price;
        this.orderStatus = orderStatus;
        setOrder(order);
        setItem(item);
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
}
