package com.example.springbootjpa.domain.order;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
}
