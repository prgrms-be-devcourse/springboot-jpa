package com.kdt.lecture.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item{

    private String writer;


    public Book(String name, int price, int stockQuantity, String writer) {
        super(name, price, stockQuantity);
        this.writer = writer;
    }
}
