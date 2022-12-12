package com.example.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "item")
@Entity
public class Item {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Item(Long id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }
}
