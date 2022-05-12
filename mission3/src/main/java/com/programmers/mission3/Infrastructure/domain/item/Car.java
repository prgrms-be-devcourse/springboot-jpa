package com.programmers.mission3.Infrastructure.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item{
    private long power;

    @Builder
    public Car(long power) {
        this.power = power;
    }
}
