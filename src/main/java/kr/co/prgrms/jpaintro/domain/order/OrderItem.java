package kr.co.prgrms.jpaintro.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.prgrms.jpaintro.domain.item.Item;
import kr.co.prgrms.jpaintro.exception.IllegalValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem(int orderPrice, int quantity) {
        checkPrice(orderPrice);
        checkQuantity(quantity);

        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public void updateItem(Item item) {
        this.item = item;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public void updateOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalValueException("[ERROR] 잘못된 주문 수량입니다!");
        }
    }

    private void checkPrice(int orderPrice) {
        if (orderPrice < 0) {
            throw new IllegalValueException("[ERROR] 잘못된 주문 금액입니다!");
        }
    }
}
