package com.example.weeklyjpa.domain.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@DiscriminatorColumn
public abstract class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue
    private Long id;

    private Long price;
    private Long stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

}
