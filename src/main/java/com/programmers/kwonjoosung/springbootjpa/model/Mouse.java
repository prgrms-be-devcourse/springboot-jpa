package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Mouse")
public class Mouse extends Item{

    @Enumerated(EnumType.STRING)
    private MouseType type;

    @Builder
    public Mouse(MouseType type, String name, int price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public MouseType getType() {
        return type;
    }

    public void changeType(MouseType type) {
        this.type = type;
    }

}
