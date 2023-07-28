package kr.co.prgrms.jpaintro.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CAR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {
    @Column(name = "power", nullable = false)
    private Integer power;

    public Car(int price, int quantity, Integer power) {
        super(price, quantity);

        checkPower(power);
        this.power = power;
    }

    private void checkPower(Integer power) {
        if (power <= 0) {
            throw new IllegalValueException("[ERROR] 마력은 0보다 커야 합니다!");
        }
    }
}
