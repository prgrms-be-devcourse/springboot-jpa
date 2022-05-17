package com.prgms.springbootjpa.domain.order;

import com.prgms.springbootjpa.domain.order.item.Item;
import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import com.prgms.springbootjpa.exception.NoAvailableQuantityException;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(Price price, Quantity quantity, Item item)
        throws NoAvailableQuantityException {
        item.consumeQuantity(quantity);

        this.price = price;
        this.quantity = quantity;
        assignItem(item);
    }

    /* 연관관계 편의 메서드 */
    public void assignOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void assignItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return getId().equals(orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
