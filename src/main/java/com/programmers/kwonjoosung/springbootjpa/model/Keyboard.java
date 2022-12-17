package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Keyboard")
public class Keyboard extends Item{

    @Enumerated(EnumType.STRING)
    private KeyboardType type;

    @Builder
    public Keyboard(KeyboardType type, String name ,int price) {
        this.type = type;
        this.name = name;
        this.price = price; // 빌더로 적용이 되나?
    }

    public KeyboardType getType() {
        return type;
    }

    public void changeType(KeyboardType type) {
        this.type = type;
    }

}
