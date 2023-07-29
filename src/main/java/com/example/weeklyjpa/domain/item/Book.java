package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("Book")
@Getter
public class Book extends Item{
    private String Author;
}
