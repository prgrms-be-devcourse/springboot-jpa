package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("BOOK")
@Getter
public class Book extends Item {
    private String author;

    public void changeAuthor() {
        this.author = author;
    }
}
