package com.example.springbootjpa.domain.main;

import com.example.springbootjpa.domain.items.Item;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "order_items")
@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String detail;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    @Builder
    public OrderItem(String detail, int quantity, Item item) {
        this.detail = detail;
        this.quantity = quantity;
        this.item = item;

        this.item.reduceQuantityInStockBy(this.quantity);     // 동시성 문제 발생?
        this.totalPrice = this.item.getPrice() * this.quantity;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
