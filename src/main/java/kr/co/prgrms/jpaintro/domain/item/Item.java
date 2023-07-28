package kr.co.prgrms.jpaintro.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public Item(int price, int quantity) {
        checkPrice(price);
        checkQuantity(quantity);

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
