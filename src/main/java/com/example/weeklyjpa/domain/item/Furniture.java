package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("Furniture")
@Getter
public class Furniture extends Item{
    private String brand;
}
