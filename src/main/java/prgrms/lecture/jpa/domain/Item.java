package prgrms.lecture.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private long price;
    @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    private long stockQuantity;

    public Item(long price, long stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
