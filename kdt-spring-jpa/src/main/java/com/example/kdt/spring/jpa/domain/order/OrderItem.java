package com.example.kdt.spring.jpa.domain.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Long id;

    @Min(value = 1, message = "가격은 최소 1원입니다.")
    private int itemPrice;

    @Min(value = 1, message = "주문수량은 최소 1개입니다.")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(Item item, int quantity) {
        if(Objects.nonNull(this.item)) {
            item.incrementStockQuantity(item.getOrderItems()
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("consistency problem!"))
                .quantity);
            item.getOrderItems().remove(this);
        }
        this.quantity = quantity;
        this.item = item;
        this.itemPrice = item.getPrice();
        item.decrementStockQuantity(quantity);
    }

    public void attachOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }
}
