package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int quantity;

    public OrderItem(int orderPrice, int quantity) {
        validateOrderPrice(orderPrice);
        validateQuantity(quantity);
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public static OrderItem create(Item item, int orderPrice, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.updateItem(item);
        orderItem.updateOrderPrice(orderPrice);
        orderItem.updateQuantity(quantity);

        item.removeStock(quantity);
        return orderItem;
    }

    public void updateItem(Item item) {
        this.item = item;
    }

    public void updateOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addToOrder(Order order) {
        this.order = order;
    }

    private void validateOrderPrice(int orderPrice) {
        if (orderPrice < 0) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ORDER_PRICE);
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ORDER_QUANTITY);
        }
    }
}
