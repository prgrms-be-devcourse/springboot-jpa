package com.kdt.mission1.domain.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int stockQuantity;
}
