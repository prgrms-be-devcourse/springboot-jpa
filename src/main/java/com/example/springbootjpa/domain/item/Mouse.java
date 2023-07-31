package com.example.springbootjpa.domain.item;

import com.example.springbootjpa.golbal.RegexPattern;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_MOUSE_COLOR;

@DiscriminatorValue("MOUSE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mouse extends Item {

    @Column(nullable = false, length = 50)
    private String color;

    public Mouse(int price, int stockQuantity, String color) {
        super(price, stockQuantity);

        validateColor(color);
        this.color = color;
    }

    private void validateColor(String color) {
        if (!StringUtils.hasText(color)
                || !RegexPattern.STRING_REGEX_PATTERN.matcher(color).matches()) {
            throw new InvalidDomainConditionException(INVALID_MOUSE_COLOR);
        }
    }
}
