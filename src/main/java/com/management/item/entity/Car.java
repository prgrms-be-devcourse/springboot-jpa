package com.management.item.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "car")
public class Car extends Item {
    @Column(name = "power", nullable = false)
    private int power;
}
