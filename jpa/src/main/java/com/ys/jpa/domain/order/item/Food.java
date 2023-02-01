package com.ys.jpa.domain.order.item;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

@Getter
@Entity
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {

    @Column(nullable = false)
    private String chef;

    public Food(int price, int stockQuantity, String chef) {
        super(price, stockQuantity);
        validateChef(chef);
        this.chef = chef;
    }

    private void validateChef(String chef) {
        Assert.hasText(chef, "chef는 빈 값이면 안됩니다.");
    }

}
