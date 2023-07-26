//package com.example.weeklyjpa.domain.item;
//
//import com.example.weeklyjpa.domain.order.OrderItem;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "items")
//@Inheritance
//public abstract class Item {
//    @Id
//    private Long id;
//
//    private int price;
//    private int stockQuantity;
//
//    @ManyToOne
//    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
//    private OrderItem orderItem;
//
//
//}
