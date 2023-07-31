package com.example.springjpamission.customer.domain;

import com.example.springjpamission.gobal.BaseEntity;
import com.example.springjpamission.order.domain.Order;
import com.example.springjpamission.order.domain.OrderItem;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.MediaSize.NA;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer( Name name) {
        this.name = name;
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
    }

}
