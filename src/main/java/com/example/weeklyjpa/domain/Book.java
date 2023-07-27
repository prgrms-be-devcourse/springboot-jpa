package com.example.weeklyjpa.domain;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item{
    private String author;
}
