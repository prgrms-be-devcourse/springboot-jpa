package com.example.weeklyjpa.domain.item;

import com.example.weeklyjpa.domain.item.Item;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item {
    private String author;
}
