package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE")
public class Phone extends Item{
    private String company;
}
