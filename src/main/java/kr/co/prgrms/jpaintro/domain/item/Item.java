package kr.co.prgrms.jpaintro.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import kr.co.prgrms.jpaintro.exception.IllegalNameException;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Column(name = "item_price", nullable = false)
    private int price;

    @Column(name = "item_quantity", nullable = false)
    private int quantity;

    public Item(String name, int price, int quantity) {
        checkName(name);
        checkPrice(price);
        checkQuantity(quantity);

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void reduceQuantity(int quantity) {
        int reducedQuantity = this.quantity - quantity;
        if (reducedQuantity < 0) {
            throw new IllegalValueException("[ERROR] 물건의 수량이 부족합니다!");
        }
        this.quantity = reducedQuantity;
    }

    private void checkName(String name) {
        checkEmptyName(name);
        checkIllegalCharacter(name);
    }

    private void checkEmptyName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalNameException("[ERROR] 이름 값은 비어있을 수 없습니다!");
        }
    }

    private void checkIllegalCharacter(String name) {
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";
        if (!Pattern.matches(pattern, name)) {
            throw new IllegalNameException("[ERROR] 이름 값은 한글과 영문만 가능합니다!");
        }
    }

    private void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalValueException("[ERROR] 잘못된 주문 수량입니다!");
        }
    }

    private void checkPrice(int orderPrice) {
        if (orderPrice < 0) {
            throw new IllegalValueException("[ERROR] 잘못된 주문 금액입니다!");
        }
    }
}
