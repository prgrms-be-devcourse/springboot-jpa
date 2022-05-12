package com.prgms.mission_jpa.customer;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int quantity;

    @Builder
    public OrderItem(int price, int quantity, Order order, Item item) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }
}
