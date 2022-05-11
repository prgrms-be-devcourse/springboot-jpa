package com.example.springjpa.inherit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="parent_item")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public abstract class ParentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int stockQuantity;
}
