package com.example.springjpamission.order.domain;

import com.example.springjpamission.gobal.BaseEntity;
import jakarta.persistence.*;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "items")
@Entity
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @Column(nullable = false)
    private int stockQuantity;

    protected Item() {}

    public Item(Price price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
