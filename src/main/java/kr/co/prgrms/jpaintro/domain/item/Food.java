package kr.co.prgrms.jpaintro.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Entity
@Getter
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {
    @Column(name = "food_type", nullable = false)
    private String type;

    public Food(String name, int price, int quantity, String type) {
        super(name, price, quantity);

        checkFoodType(type);
        this.type = type;
    }

    private void checkFoodType(String foodName) {
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";
        if (!StringUtils.hasText(foodName) || !Pattern.matches(pattern, foodName)) {
            throw new IllegalValueException("[ERROR] 음식 종류가 잘못 됐습니다!");
        }
    }
}
