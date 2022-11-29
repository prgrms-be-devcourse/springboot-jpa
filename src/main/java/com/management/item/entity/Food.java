package com.management.item.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "food")
public class Food extends Item {
    @Column(name = "chef", nullable = false)
    private String chef;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return Objects.equals(chef, food.chef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chef);
    }
}
