package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

import static com.prgrms.springbootjpa.global.util.Validator.validateNumberSize;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(int price, OrderStatus orderStatus) {
        validateOrderItem(price, orderStatus);
        this.price = price;
        this.orderStatus = orderStatus;
    }

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if(Objects.nonNull(this.item)) {
            this.item.orderItems.remove(this);
        }

        this.item = item;
        item.orderItems.add(this);
    }

    private void validateOrderItem(int price, OrderStatus orderStatus) {
        if(!validateNumberSize(price, 100)) {
            throw new WrongFieldException("OrderItem.price", price, "100 <= price");
        }

        if(orderStatus == null) {
            throw new WrongFieldException("OrderItem.orderStatus", orderStatus, "orderStatus is required field");
        }
    }
}
