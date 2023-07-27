package com.programmers.jpabasic.domain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.programmers.jpabasic.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Column(nullable = false)
    private int price;

    @Column
    private int stockQuantity;
}
