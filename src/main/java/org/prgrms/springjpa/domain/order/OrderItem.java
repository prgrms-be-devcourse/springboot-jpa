package org.prgrms.springjpa.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static OrderItem createOrderItem(Item item, int orderPrice, int orderQuantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setOrderQuantity(orderQuantity);
        item.removeStock(orderQuantity);
        return orderItem;
    }

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)){
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if(Objects.nonNull(this.item)) {
            item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }
}
