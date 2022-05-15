package com.example.springbootjpa.domain.items;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "fruit")
@Entity
public class Fruit extends Item {

    @Column(name = "origin")
    private String origin;

    @Builder
    public Fruit(@NonNull String uuid,
                 @NonNull String productName,
                 @NonNull int quantityInStock,
                 @NonNull int price,
                 @NonNull String origin) {
        super(uuid, productName, quantityInStock, price);
        this.origin = origin;
    }

}
