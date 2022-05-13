package org.programmers.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Food extends Item {
    private String chef;
}
