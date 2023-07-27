package com.example.weeklyjpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public abstract class Item {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;
    private Long stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();
}
