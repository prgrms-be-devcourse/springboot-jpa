package org.devcourse.assignment.domain.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.devcourse.assignment.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "order_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus;

    @Column(name = "memo")
    @Lob
    private String memo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Column(name = "order_items")
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Customer customer;

    @Builder
    public Order(String memo, List<OrderItem> orderItems, Customer customer) {
        this.orderStatus = OrderStatus.COMPLETED;
        this.memo = memo;
        this.orderItems = (orderItems == null)? new ArrayList<>() : orderItems;
        this.customer = customer;
        this.orderDateTime = LocalDateTime.now();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
