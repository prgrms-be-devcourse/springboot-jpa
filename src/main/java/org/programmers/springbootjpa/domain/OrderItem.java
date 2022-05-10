package org.programmers.springbootjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
