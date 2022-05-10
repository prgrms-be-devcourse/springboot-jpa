package org.programmers.springbootjpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int stockQuantity;
}
