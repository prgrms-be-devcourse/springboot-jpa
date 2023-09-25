package com.programmers.june.jpastudy.domain.order_item.entity;

import com.programmers.june.jpastudy.domain.item.entity.Item;
import com.programmers.june.jpastudy.domain.order.entity.Order;
import com.programmers.june.jpastudy.domain.order_item.model.OrderStatus;
import com.programmers.june.jpastudy.global.BaseEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price; // 주문 가격
    private int quantity;   // 주문 수량

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Builder
    public OrderItem(int price, int quantity, Item item) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    // 주문 생성 메서드
    public static OrderItem createOrderItem(Item item, int price, int orderQuantity) {
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .price(price)
                .quantity(orderQuantity)
                .build();

        item.removeStockQuantity(orderQuantity);

        return orderItem;
    }

    public void cancle() {
        this.item.addStockQuantity(quantity);
    }
}
