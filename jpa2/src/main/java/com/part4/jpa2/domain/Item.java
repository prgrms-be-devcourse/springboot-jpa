package com.part4.jpa2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    private Long id;

    private int price;
    private int stockQuantity;

}
