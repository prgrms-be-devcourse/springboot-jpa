package com.example.mission3.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int stock = 0;

    @Lob
    private String description;

    @Column(nullable = false)
    private long price;

    public Item(String name, long price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, long price, int stock,String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public void changeStock(int stock) {
        this.stock = stock;
    }
}
