package com.kdt.springbootjpa.item.domain;

import com.kdt.springbootjpa.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int stockQuantity;

    protected Item() {
    }

    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
