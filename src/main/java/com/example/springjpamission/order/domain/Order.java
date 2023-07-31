package com.example.springjpamission.order.domain;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.gobal.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    private String id;
    private String memo;
    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(String id, String memo, int price, int quantity, Customer customer) {
        this.id = id;
        this.memo = memo;
        this.price = price;
        this.quantity = quantity;
        this.customer = customer;
    }

    public void setCustomer(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            this.customer.getOrders().remove(this);
        }

        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

}
