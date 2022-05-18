package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long itemId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="quantity", nullable = false)
    private Long quantity;

    @Column(name="price", nullable = false)
    private Long price;
}
