package com.example.springbootjpa.domain.items;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "book")
@Entity
public class Book extends Item {

    @Column(name = "author")
    private String author;

    @Builder
    public Book(@NonNull String uuid,
                @NonNull String productName,
                @NonNull int quantityInStock,
                @NonNull int price,
                @NonNull String author) {
        super(uuid, productName, quantityInStock, price);
        this.author = author;
    }

}
