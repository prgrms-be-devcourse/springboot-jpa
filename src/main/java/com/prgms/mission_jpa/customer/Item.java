package com.prgms.mission_jpa.customer;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int stockQuantity;

    @Builder
    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    @OneToMany(mappedBy = "item",orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }
}
