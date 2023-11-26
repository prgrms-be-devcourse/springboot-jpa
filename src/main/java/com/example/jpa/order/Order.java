package com.example.jpa.order;

import com.example.jpa.customer.Customer;
import com.example.jpa.orderItem.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime orderDatetime;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;
    //customer_fk
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void setMember(Customer customer) {
        if(Objects.nonNull(this.customer)) {
            this.customer.getOrders().remove(this);
        }

        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}
