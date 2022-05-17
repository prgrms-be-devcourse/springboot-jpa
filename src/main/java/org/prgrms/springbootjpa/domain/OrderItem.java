package org.prgrms.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    public OrderItem(){}

    public OrderItem(Product product, int price, int quantity){
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    // TODO : 이 validation이 왜 다대일에서 다측에서 일어나야 하는거지? order에서 처리하면 get 없이 할 수 있는거 아닌가..
    public void setOrder(Order order) {
        if(this.order != null){
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setProduct(Product product){
        if(this.product != null){
            this.product.getProductOrders().remove(this);
        }
        this.product = product;
        product.getProductOrders().add(this);
    }
}
