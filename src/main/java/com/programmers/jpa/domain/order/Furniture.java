package com.programmers.jpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item {
    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Builder
    private Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
