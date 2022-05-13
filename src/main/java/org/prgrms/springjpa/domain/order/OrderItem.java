package org.prgrms.springjpa.domain.order;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
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
        orderItem.changeItem(item);
        orderItem.changeOrderPrice(orderPrice);
        orderItem.changeOrderQuantity(orderQuantity);
        item.removeStock(orderQuantity);
        return orderItem;
    }

    public void changeOrder(Order order) {
        if(Objects.nonNull(this.order)){
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void changeItem(Item item) {
        if(Objects.nonNull(this.item)) {
            item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }

    public void changeOrderPrice(int orderPrice) {
        if(orderPrice < 0) {
            throw new IllegalArgumentException("주문가격은 0원 미만이 될 수 없습니다.");
        }
        this.orderPrice = orderPrice;
    }

    public void changeOrderQuantity(int orderQuantity) {
        if(orderQuantity < 0) {
            throw new IllegalArgumentException("주문수량은 0개 미만이 될 수 없습니다");
        }
        this.orderQuantity = orderQuantity;
    }
}
