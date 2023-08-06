package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private int orderItemPrice;

    private int orderItemQuantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private static final int ZERO_STOCK = 0;

    private OrderItem(Item item, int orderItemQuantity){
        this.item = item;
        this.orderItemQuantity = validatePositiveQuantity(orderItemQuantity);
        this.orderItemPrice = orderItemQuantity * item.getPrice();
    }

    public static OrderItem createOrderItem(Item item, int orderItemQuantity) {
        OrderItem orderItem = new OrderItem(item, orderItemQuantity);

        item.deductQuantity(orderItemQuantity);

        return orderItem;
    }

    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    private int validatePositiveQuantity(int orderItemQuantity){
        if(orderItemQuantity < ZERO_STOCK){
            throw new IllegalArgumentException("재고 부족");
        }
        return orderItemQuantity;
    }

}
