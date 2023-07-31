package com.blackdog.springbootjpa.domain.item.model;

import com.blackdog.springbootjpa.domain.item.vo.OriginNation;
import com.blackdog.springbootjpa.domain.item.vo.Price;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "item")
@Entity
@Getter
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @Embedded
    private Price price;

    @Embedded
    private OriginNation nation;

    @Builder
    public Item(Price price, OriginNation nation) {
        this.price = price;
        this.nation = nation;
    }
}
