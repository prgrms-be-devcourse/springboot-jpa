package com.programmers.mission3.Infrastructure.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item{
    private String chef;

    @Builder
    public Food(String chef) {
        this.chef = chef;
    }
}
