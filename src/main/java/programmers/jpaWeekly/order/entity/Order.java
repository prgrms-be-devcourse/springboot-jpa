package programmers.jpaWeekly.order.entity;

import jakarta.persistence.*;
import programmers.jpaWeekly.customer.entity.Customer;
import programmers.jpaWeekly.order.entity.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static programmers.jpaWeekly.order.entity.constant.OrderStatus.OPENED;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order() {
        orderStatus = OPENED;
        createdAt = LocalDateTime.now();
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }
}
