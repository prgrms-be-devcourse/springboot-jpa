package com.kdt.lecture.domain.order;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class EntityRegister {
    public static Member member(EntityManager entityManager) {
        Member member = new Member();

        member.setName("yongcheol");
        member.setAge(100);
        member.setAddress("xx시 xx구 xx동");
        member.setNickName("yongckim");
        member.setDescription("백둥이");
        entityManager.persist(member);
        return member;
    }

    public static Item item() {
        Item item = new Item();

        item.setPrice(1000);
        item.setStockQuantity(100);
        return item;
    }

    public static Order order(EntityManager entityManager) {
        Order order = new Order();

        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        entityManager.persist(entityManager);
        return order;
    }

    public static OrderItem orderItem() {
        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(2);
        orderItem.setPrice(1000);
        return orderItem;
    }
}
