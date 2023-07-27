package com.example.weeklyjpa.domain.item;

import com.example.weeklyjpa.domain.order.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Getter
@Table(name = "items")
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;


}
