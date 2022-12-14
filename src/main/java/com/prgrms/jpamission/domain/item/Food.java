package com.prgrms.jpamission.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("FOOD")
public class Food extends Item{
    private String chef;
}
