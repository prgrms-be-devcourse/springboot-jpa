package com.example.chapter1.domain.item.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseIdEntity {

    private int price;
    private int stockQuantity;
}
