package org.prgrms.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> productOrders = new ArrayList<>();

    public Product(){}

    public Product(int price, int stockQuantity){
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addProductOrder(OrderItem orderItem){
        orderItem.setProduct(this);
    }
}
