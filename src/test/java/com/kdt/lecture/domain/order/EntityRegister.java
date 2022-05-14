package com.kdt.lecture.domain.order;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class EntityRegister {
    private static int cnt = 1;

    public static Member member(EntityManager entityManager) {
        Member member = new Member();

        member.setName("yongcheol");
        member.setAge(100);
        member.setAddress("xx시 xx구 xx동");
        member.setNickName("yongckim" + cnt++);
        member.setDescription("백둥이");
        entityManager.persist(member);
        return member;
    }

    public static Item item(EntityManager entityManager) {
        Item item = new Item();

        item.setPrice(1000);
        item.setStockQuantity(100);
        entityManager.persist(item);
        return item;
    }

    public static Order order(EntityManager entityManager) {
        Order order = new Order();

        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("test");
        entityManager.persist(order);
        return order;
    }

    public static OrderItem orderItem(EntityManager entityManager) {
        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(2);
        orderItem.setPrice(1000);
        entityManager.persist(orderItem);
        return orderItem;
    }
}
