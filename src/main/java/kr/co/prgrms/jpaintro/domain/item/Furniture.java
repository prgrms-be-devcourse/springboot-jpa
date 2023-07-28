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
@DiscriminatorValue("FURNITURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item {
    @Column(name = "width", nullable = false)
    private Integer width;
    @Column(name = "height", nullable = false)
    private Integer height;

    public Furniture(int price, int quantity, Integer width, Integer height) {
        super(price, quantity);

        checkLength(width);
        checkLength(height);
        this.width = width;
        this.height = height;
    }

    private void checkLength(Integer length) {
        if (length <= 0) {
            throw new IllegalValueException("[ERROR] 길이는 0보다 커야 합니다!");
        }
    }
}
