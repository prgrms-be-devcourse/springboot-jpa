package com.example.chapter1.domain.item.domain;

import com.example.chapter1.domain.base.BaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item extends BaseIdEntity {

    private int price;
    private int stockQuantity;
}
