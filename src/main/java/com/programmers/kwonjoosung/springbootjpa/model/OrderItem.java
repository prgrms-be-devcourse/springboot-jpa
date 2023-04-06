package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString(exclude = {"order"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @OneToOne(fetch = FetchType.EAGER) // cascade = CascadeType.ALL 이 필요한가?
    @JoinColumn(name = "order_item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public void addOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
    }

    public void addStock(int quantity) {
        this.quantity += quantity;
    }

    public void changeStockQuantity(int stockQuantity) {
        this.quantity = stockQuantity;
    }

    public void removeStock(int quantity) {
        this.quantity = this.quantity - quantity;
    }

}
