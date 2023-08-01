package org.wonu606.jpashop.order.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import org.wonu606.jpashop.item.domain.Item;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // final은 강력한 제어자
    private Integer paymentAmount;
    private Integer quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public OrderLineItem(Integer paymentAmount, Integer quantity, Item item) {
        this.paymentAmount = paymentAmount;
        this.quantity = quantity;
        this.item = item;
    }

    public OrderLineItem() {
    }

    public OrderLineItem(Long id, Integer paymentAmount, Integer quantity, Item item, Order order) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.quantity = quantity;
        this.item = item;
        this.order = order;
    }

    public OrderLineItem(Integer paymentAmount, Integer quantity, Item item, Order order) {
        this(null, paymentAmount, quantity, item, order);
    }

    // setter 이름 변경
    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.removeOrderLineItem(this);
        }

        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderLineItem{" +
                "id=" + id +
                ", paymentAmount=" + paymentAmount +
                ", quantity=" + quantity +
                ", item=" + item +
                ", order.id=" + order.getId() +
                '}';
    }
}
