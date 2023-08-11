package com.example.springjpamission.order.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Price{

    private int cost;

    protected Price() { }

    public Price(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("가격 : " + cost);
        }
        this.cost = cost;
    }

    public int getCost(){
        return cost;
    }

}
