package org.wonu606.jpashop.item.domain;

import jakarta.persistence.Entity;

@Entity
public class PerformanceTicket extends Item {

    private String name;

    public PerformanceTicket() {
    }

    public PerformanceTicket(Long id, Integer price, Integer stockQuantity, String name) {
        super(id, price, stockQuantity);
        this.name = name;
    }

    public PerformanceTicket(Integer price, Integer stockQuantity, String name) {
        super(null, price, stockQuantity);
        this.name = name;
    }

    @Override
    public String toString() {
        return "PerformanceTicket{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
