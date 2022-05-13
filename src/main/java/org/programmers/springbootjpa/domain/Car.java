package org.programmers.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Car extends Item{
    private int power;
}
