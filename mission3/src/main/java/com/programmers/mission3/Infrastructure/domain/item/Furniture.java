package com.programmers.mission3.Infrastructure.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item{
    private long width;
    private long height;

    @Builder
    public Furniture(long width, long height) {
        this.width = width;
        this.height = height;
    }
}
