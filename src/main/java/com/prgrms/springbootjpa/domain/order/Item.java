package com.prgrms.springbootjpa.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.prgrms.springbootjpa.global.util.EntityFieldValidator.validateItemField;

@Entity
@Table(name = "item")
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    List<OrderItem> orderItems = new ArrayList<>();

    protected Item() {
    }

    public Item(String name, int price, int stockQuantity) {
        validateItemField(name, price, stockQuantity);
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }
}
