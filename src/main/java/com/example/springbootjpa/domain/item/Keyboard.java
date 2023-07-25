package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_KEYBOARD_COLOR;

@DiscriminatorValue("KEYBOARD")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyboard extends Item {

    @Column(nullable = false)
    private String color;

    public Keyboard(int price, int stockQuantity, String color) {
        super(price, stockQuantity);

        validateColor(color);
        this.color = color;
    }

    private void validateColor(String color) {
        if (!StringUtils.hasText(color)) {
            throw new InvalidDomainConditionException(INVALID_KEYBOARD_COLOR);
        }
    }
}
