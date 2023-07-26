package com.prgms.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Long version;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    public Item(Long price, Long quantity, String description) {
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public void decreaseQuantity(Long count) {
        validateQuantity(count);
        this.quantity -= count;
    }

    private void validateQuantity(Long count) {
        if (this.quantity < count) {
            throw new IllegalArgumentException("재고 부족");
        }
    }
    Item() {}

    public Long getId() {
        return id;
    }
}
