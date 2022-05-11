package com.example.chapter1.domain.order.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem extends BaseIdEntity {

    private int price;
    private int quantity;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "item_id")
    private Long itemId;
}
