package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidOrderItemException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.item = item;
    }

    public void orderItem(int count) {
        validateCount(count);
        this.count = count;
        item.decreaseStock(count);
    }

    private void validateCount(int count) {
        if (item.isMoreThanStock(count)) {
            throw new InValidOrderItemException("재고보다 많은 수량을 주문할 수 없습니다.");
        }
    }

    public void changeOrder(Order order) {
        this.order = order;
    }
}
