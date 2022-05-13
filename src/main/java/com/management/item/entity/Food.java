package com.management.item.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "food")
public class Food extends Item {
    @Column(name = "chef", nullable = false)
    private String chef;
}
