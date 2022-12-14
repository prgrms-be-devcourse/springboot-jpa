package com.prgrms.m3.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item {
    private int width;
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }

    public void changeWidth(int width) {
        this.width = width;
    }

    public void changeHeight(int height) {
        this.height = height;
    }
}