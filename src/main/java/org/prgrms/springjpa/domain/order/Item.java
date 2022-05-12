package org.prgrms.springjpa.domain.order;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int remainQuantity = stockQuantity - quantity;
        if(remainQuantity < 0) {
            throw new IllegalArgumentException("재고수량은 0 미만이 될 수 없습니다");
        }
        this.stockQuantity = remainQuantity;
    }

}
