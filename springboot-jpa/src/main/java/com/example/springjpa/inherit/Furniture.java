package com.example.springjpa.inherit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@DiscriminatorValue("furniture")
public class Furniture extends ParentItem {
    private long width;
    private long height;
}
