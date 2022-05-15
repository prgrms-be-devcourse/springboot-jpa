package org.prgrms.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> productOrders;

    public void addProductOrder(OrderItem orderItem){
        orderItem.setProduct(this);
    }
}
