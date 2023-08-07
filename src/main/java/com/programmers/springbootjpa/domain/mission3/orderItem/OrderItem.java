package com.programmers.springbootjpa.domain.mission3.orderItem;

import com.programmers.springbootjpa.domain.mission3.BaseEntity;
import com.programmers.springbootjpa.domain.mission3.item.Item;
import com.programmers.springbootjpa.domain.mission3.order.Order;
import com.programmers.springbootjpa.global.exception.InvalidRequestValueException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class OrderItem extends BaseEntity {

    private static final int MINIMUM_QUANTITY_LIMIT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int quantity, Item item) {
        checkQuantity(quantity, item);

        this.price = item.getPrice();
        this.quantity = quantity;
        this.item = item;
    }

    private void checkPrice(int price, Item item) {
        if (price != item.getPrice()) {
            throw new InvalidRequestValueException("금액은 선택한 Item의 금액과 다를 수 없습니다.");
        }
    }

    private void checkQuantity(int quantity, Item item) {
        if (quantity < MINIMUM_QUANTITY_LIMIT || quantity > item.getStockQuantity()) {
            throw new InvalidRequestValueException("수량은 1개보다 적거나 재고 수량보다 많을 수 없습니다.");
        }
    }

    public void update(int quantity, Item item) {
        checkQuantity(quantity, item);

        this.price = item.getPrice();
        this.quantity = quantity;
        this.item = item;
    }
}
