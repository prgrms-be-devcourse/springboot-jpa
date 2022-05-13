package com.kdt.lecture.domain;

import com.kdt.lecture.domain.item.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orderItem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(Item item, int price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem createOrderItem(Item item, int count) {
        item.removeStock(count);
        return new OrderItem(item, item.getPrice(), count);
    }

    public void addOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void cancel() {
        this.getItem().addStock(this.quantity);
        this.order.getOrderItems().remove(this);
        this.order = null;
    }

    public int getTotalPrice() {
        return this.price * this.quantity;
    }

}
