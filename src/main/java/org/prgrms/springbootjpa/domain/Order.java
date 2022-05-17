package org.prgrms.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO : 영속성 전이

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id
    @Column(name = "order_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 10)
    private OrderStatus orderStatus;

    @Lob
    @Column(length = 1500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    // 기본값은 pk, name은 fk 이름, columnName이 join되는 테이블 컬럼명

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
    // OneToMany에서는 fetchType이 Lazy가 기본임.

    public Order(){}

    public Order(String id, OrderStatus orderStatus, Customer customer) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.customer = customer;
    }

    //Q. 연관관계 주인이 CRUD 관리 - 편의 메소드와는 관계 없는건지.

    public void setCustomer(Customer customer) {
        if(this.customer != null){
            this.customer.getOrders().remove(this);
        }
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItem.setOrder(this);
    }
}
