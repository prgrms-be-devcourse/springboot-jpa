package com.example.chapter1.domain.order.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OrderItem extends BaseIdEntity {

    private int price;
    private int quantity;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "item_id")
    private Long itemId;
}
