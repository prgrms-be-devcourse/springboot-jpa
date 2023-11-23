package com.example.kdtjpa.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FURNITURE")
@SuperBuilder
public class Furniture extends Item {
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
}
