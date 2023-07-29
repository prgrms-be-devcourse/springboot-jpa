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

    private OrderItem(Item item, int orderItemQuantity){
        this.item = item;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemPrice = orderItemQuantity * item.getPrice(); //질문
    }

    public static OrderItem createOrderItem(Item item, int orderItemQuantity) { //질문
        OrderItem orderItem = new OrderItem(item, orderItemQuantity);

        item.deductQuantity(orderItemQuantity);

        return orderItem;
    }

    //연관관계 편의 메서드
    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

}
