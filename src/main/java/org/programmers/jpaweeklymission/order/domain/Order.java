package org.programmers.jpaweeklymission.order.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.jpaweeklymission.customer.Customer;
import org.programmers.jpaweeklymission.global.BaseEntity;

import java.util.List;
import java.util.Objects;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private OrderStatus status;

    @Lob
    @Column(name = "memo")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Builder
    public Order(OrderStatus status, String memo) {
        this.status = status;
        this.memo = memo;
    }

    public void setCustomer(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            customer.removeOrder(this);
        }
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}
