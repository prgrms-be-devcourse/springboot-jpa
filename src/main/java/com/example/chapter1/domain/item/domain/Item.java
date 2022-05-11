package com.example.chapter1.domain.item.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseIdEntity {

    @Column(name = "price")
    private int price;

    @Column(name = "stock_quantity")
    private int stockQuantity;
}
