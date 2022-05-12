package com.part4.jpa2;

import com.part4.jpa2.domain.order.Member;
import com.part4.jpa2.domain.order.Order;
import com.part4.jpa2.domain.order.OrderItem;
import com.part4.jpa2.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.part4.jpa2.domain.order.OrderStatus.OPENED;

public class Helper {
    public static Order makeOrder(){
        var order = new Order();
        order.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        order.setCreatedBy("SU");

        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("메모장");
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        return order;
    }
    public static Order makeOrderWithUUID(String uuid){
        var order = new Order();
        order.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        order.setCreatedBy("SU");

        order.setUuid(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        return order;
    }
    public static Member makeMember(){
        var member = new Member();
        member.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        member.setCreatedBy("SU");

        member.setName("kimsubin");
        member.setNickName(UUID.randomUUID().toString().substring(10));
        member.setAge(24);
        member.setAddress("게더타운");
        member.setDescription("배고프다");
        return member;
    }
    public static OrderItem makeOrderItem(){
        var item = new OrderItem();
        item.setQuantity(10);
        item.setPrice(1000);
        return item;
    }
}
