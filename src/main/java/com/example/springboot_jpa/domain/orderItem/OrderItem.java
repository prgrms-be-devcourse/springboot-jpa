package com.example.springboot_jpa.domain.orderItem;

import com.example.springboot_jpa.domain.item.Item;
import com.example.springboot_jpa.domain.order.Order;
import com.example.springboot_jpa.global.BaseEntity;
import jakarta.persistence.*;

@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    // 연관 관계
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // 연관관계 편의 메소드
    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItems().remove(this); // order -> orderItmes
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

    // 기본 생성자

    public OrderItem() {
    }

    //

    public OrderItem(int quantity) {
        this.quantity = quantity;
    }


    // getter

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }
}
