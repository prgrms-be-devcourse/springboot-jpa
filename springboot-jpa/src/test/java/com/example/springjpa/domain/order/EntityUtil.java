package com.example.springjpa.domain.order;

import java.util.UUID;

import static com.example.springjpa.domain.order.OrderStatus.OPENED;


public class EntityUtil {
    private EntityUtil() {
    }

    public static Member getNewMember() {
        return Member.builder()
                .name("태산")
                .nickName(UUID.randomUUID().toString())
                .age(1)
                .address("주소")
                .description("Desc")
                .orders(new Orders())
                .build();
    }

    public static Order getNewOrder(Member member) {
        return Order.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("memo")
                .orderStatus(OPENED)
                .member(member)
                .build();
    }

    public static OrderItem getNewOrderItem(int price, int quantity, Order order) {
        return OrderItem.builder().price(price).quantity(quantity).order(order).build();
    }

}
