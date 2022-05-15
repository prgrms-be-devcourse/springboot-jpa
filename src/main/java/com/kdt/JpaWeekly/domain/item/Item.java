package com.kdt.JpaWeekly.domain.item;

import com.kdt.JpaWeekly.common.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int price;

    private int stockQuantity;

    protected Item() {
        super();
    }

    protected Item(String createdBy, LocalDateTime createdAt, int price, int stockQuantity) {
        super(createdBy, createdAt);
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
