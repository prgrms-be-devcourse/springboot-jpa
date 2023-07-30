package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem(int orderPrice, int quantity) {
        validateOrderPrice(orderPrice);
        validateQuantity(quantity);
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public static OrderItem create(Item item, int orderPrice, int quantity) {
        item.validateRequestQuantity(quantity);

        OrderItem orderItem = new OrderItem(orderPrice, quantity);
        orderItem.updateItem(item);

        return orderItem;
    }

    public void updateItem(Item item) {
        this.item = item;
    }

    public void addToOrder(Order order) {
        this.order = order;
    }

    public int getTotalPrice() {
        return getOrderPrice() * getQuantity();
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
