package com.example.kdtjpa.domain.orderItem;

import com.example.kdtjpa.domain.BaseEntity;
import com.example.kdtjpa.domain.item.Item;
import com.example.kdtjpa.domain.order.Order;
import javax.persistence.*;
import java.util.Objects;
import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int price;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(int price, int quantity, Item item) {
        super(null, now());
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    /** 연관관계 매핑 **/
    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
    }
}
