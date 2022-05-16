package com.kdt.lecture.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price")
    private int price;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne
    private Item item;

    public OrderItem() {
    }

    public OrderItem(OrderStatus orderStatus, Order order, Item item) {
        this.price = item.getTotalPrice();
        this.orderStatus = orderStatus;
        setOrder(order);
        setItem(item);
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getItems().remove(this);
        }
        this.order = order;
        order.getItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
