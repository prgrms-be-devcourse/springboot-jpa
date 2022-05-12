package com.prgms.mission_jpa.testUtils;

import com.prgms.mission_jpa.customer.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestDomainCreatorUtils {
    public static Member createMember(){
        return Member.builder()
                .name("yong")
                .nickName("dragon")
                .address("경남 창원")
                .age(27)
                .description("---")
                .build();
    }

    public static Order createOrder(){
        return Order.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("문앞에 놔두세요")
                .orderStatus(OrderStatus.OPENED)
                .orderDateTime(LocalDateTime.now())
                .build();
    }

    public static OrderItem createOrderItem(){
        return OrderItem.builder()
                .price(10000)
                .quantity(2)
                .build();
    }

    public static Item createItem(){
        return Item.builder()
                .price(5000)
                .stockQuantity(200)
                .build();
    }
}
