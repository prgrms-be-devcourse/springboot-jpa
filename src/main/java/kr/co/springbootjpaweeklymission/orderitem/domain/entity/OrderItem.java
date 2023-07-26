package kr.co.springbootjpaweeklymission.orderitem.domain.entity;

import jakarta.persistence.*;
import kr.co.springbootjpaweeklymission.item.domain.entity.Item;
import kr.co.springbootjpaweeklymission.order.domain.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_orders_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", length = 4, nullable = false, unique = true)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;
}
