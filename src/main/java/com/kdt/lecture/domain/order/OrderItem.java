package com.kdt.lecture.domain.order;

import lombok.Getter;

import javax.persistence.*;

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

    public OrderItem(){}

    public OrderItem(int price, OrderStatus orderStatus, Order order, Item item) {
        this.price = price;
        this.orderStatus = orderStatus;
        this.order = order;
        this.item = item;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.addOrderItem(this);
    }

    public void setItem(Item item) {
        this.item=item;
    }
}
