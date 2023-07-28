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
    @Column(name = "food_name", nullable = false)
    private String foodName;

    public Food(int price, int quantity, String foodName) {
        super(price, quantity);

        checkFoodName(foodName);
        this.foodName = foodName;
    }

    private void checkFoodName(String foodName) {
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";
        if (!StringUtils.hasText(foodName) || !Pattern.matches(pattern, foodName)) {
            throw new IllegalValueException("[ERROR] 음식 이름이 잘못 됐습니다!");
        }
    }
}
