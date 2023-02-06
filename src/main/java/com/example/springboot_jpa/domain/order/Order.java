package com.example.springboot_jpa.domain.order;

import com.example.springboot_jpa.domain.orderItem.OrderItem;
import com.example.springboot_jpa.global.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 기본 생성자

    public Order() {
    }


    // AllArgus


    public Order(String address, String phoneNumber, OrderStatus orderStatus) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
    }

    // 연관관계
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem); // 이 orderItem은 내꺼야
        if (orderItem.getOrder() != null) { //근데 orderItem이 주인이 있어
            orderItem.setOrder(this); //바꿔
        }
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
