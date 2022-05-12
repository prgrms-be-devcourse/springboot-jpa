package com.example.springjpa.inherit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@Setter
@Getter
@DiscriminatorValue("car")
public class Car extends ParentItem {
    private long power;
}


