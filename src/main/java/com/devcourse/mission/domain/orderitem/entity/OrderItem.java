package com.devcourse.mission.domain.orderitem.entity;

import com.devcourse.mission.domain.item.entity.Item;
import com.devcourse.mission.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "order_item")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private OrderItem(Item item, int totalPrice, int quantity) {
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.item = item;
    }

    public static OrderItem createOrderItem(Item item, int quantity) {
        int totalPrice = item.getPrice() * quantity;
        return new OrderItem(item, totalPrice, quantity);
    }

    public void enrollOrder(Order order) {
        this.order = order;
    }

    public void unrollItem() {
        this.item.increaseQuantity(quantity);
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
        this.totalPrice = item.getPrice() * this.quantity;
    }

    public void combineFrom(OrderItem orderItem) {
        System.out.println("orderItem.getTotalPrice() be= " + orderItem.getTotalPrice());
        this.totalPrice += orderItem.getTotalPrice();
        this.quantity += orderItem.getQuantity();
        System.out.println("orderItem.getTotalPrice() af= " + totalPrice);
    }

    public boolean isSame(OrderItem orderItem) {
        return item.isSame(orderItem.getItem());
    }

    public boolean isSameItem(long itemId) {
        return this.item.isSameId(itemId);
    }
}
