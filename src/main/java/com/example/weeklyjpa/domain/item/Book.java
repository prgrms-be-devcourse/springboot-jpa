package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item {
    private String author;
}
