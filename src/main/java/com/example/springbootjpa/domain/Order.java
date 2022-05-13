package com.example.springbootjpa.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "memo")
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDate;

    // member fk
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setMember(Customer customer) {
        if (Objects.nonNull(this.customer)) {
            this.customer.getOrderList().remove(this);
        }

        this.customer = customer;
        customer.addOrder(this);

    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }


}
