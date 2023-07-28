package com.pgms.jpa.domain.order;

import com.pgms.jpa.domain.item.Item;
import com.pgms.jpa.global.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "order_item_quantity", nullable = false)
    private int orderItemQuantity; // 주문 수량

    @Column(name = "order_item_price", nullable = false)
    private int orderItemPrice;

    protected OrderItem() {

    }

    public static OrderItem createOrderItem(Item item, int orderItemQuantity) {
        OrderItem orderItem = new OrderItem(item, orderItemQuantity);
        item.removeQuantity(orderItemQuantity);
        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem(Item item, int orderItemQuantity) {
        this.item = item;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemPrice = item.getPrice() * orderItemQuantity;
    }

    public Long getId() {
        return this.id;
    }

    public Order getOrder() {
        return this.order;
    }

    public Item getItem() {
        return this.item;
    }

    public int getOrderItemQuantity() {
        return this.orderItemQuantity;
    }

    public int getOrderItemPrice() {
        return this.orderItemPrice;
    }

    public void cancel() {
        item.addStock(orderItemQuantity);
    }
}
