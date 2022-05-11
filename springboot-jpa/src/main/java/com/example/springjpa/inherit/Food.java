package com.example.springjpa.inherit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("food")
public class Food extends ParentItem {
    private String chef;

}
