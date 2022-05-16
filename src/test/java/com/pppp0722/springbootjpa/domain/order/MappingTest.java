package com.pppp0722.springbootjpa.domain.order;

import static com.pppp0722.springbootjpa.domain.order.OrderStatus.OPENED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setCreatedAt(LocalDateTime.now());
        member.setCreatedBy(".");
        member.setName("ilhwan");
        member.setNickName("ilhwanee");
        member.setAddress("산운로 37 어딘가");

        entityManager.persist(member);

        Order order1 = new Order();
        order1.setCreatedBy(".");
        order1.setCreatedAt(LocalDateTime.now());
        order1.setUuid(UUID.randomUUID().toString());
        order1.setOrderDatetime(LocalDateTime.now());
        order1.setOrderStatus(OPENED);
        order1.setMember(member);

        entityManager.persist(order1);

        Car car = new Car();
        car.setCreatedAt(LocalDateTime.now());
        car.setCreatedBy(".");
        car.setName("오빠차");
        car.setPrice(10000000);
        car.setStockQuantity(90);
        car.setPower(1000);

        entityManager.persist(car);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCreatedAt(LocalDateTime.now());
        orderItem1.setCreatedBy(".");
        orderItem1.setPrice(30000000);
        orderItem1.setQuantity(3);
        orderItem1.setItem(car);
        orderItem1.setOrder(order1);

        entityManager.persist(orderItem1);

        Order order2 = new Order();
        order2.setCreatedBy(".");
        order2.setCreatedAt(LocalDateTime.now());
        order2.setUuid(UUID.randomUUID().toString());
        order2.setOrderDatetime(LocalDateTime.now());
        order2.setOrderStatus(OPENED);
        order2.setMember(member);

        entityManager.persist(order2);

        Food food = new Food();
        food.setCreatedAt(LocalDateTime.now());
        food.setCreatedBy(".");
        food.setName("라면");
        food.setPrice(1000);
        food.setStockQuantity(20);
        food.setChef("백종원");

        entityManager.persist(food);

        Furniture furniture = new Furniture();
        furniture.setCreatedAt(LocalDateTime.now());
        furniture.setCreatedBy(".");
        furniture.setName("이케아 책상");
        furniture.setPrice(100000);
        furniture.setStockQuantity(10);
        furniture.setWidth(100);
        furniture.setHeight(100);

        entityManager.persist(furniture);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setCreatedAt(LocalDateTime.now());
        orderItem2.setCreatedBy(".");
        orderItem2.setPrice(5000);
        orderItem2.setQuantity(5);
        orderItem2.setItem(food);
        orderItem2.setOrder(order2);

        entityManager.persist(orderItem2);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setCreatedAt(LocalDateTime.now());
        orderItem3.setCreatedBy(".");
        orderItem3.setPrice(100000);
        orderItem3.setQuantity(1);
        orderItem3.setItem(furniture);
        orderItem3.setOrder(order2);

        entityManager.persist(orderItem3);

        transaction.commit();

        Member foundMember = entityManager.find(Member.class, 1L);
        List<Order> foundOrders = foundMember.getOrders();
        for (int i = 0; i < foundOrders.size(); i++) {
            List<OrderItem> orderItems = foundOrders.get(i).getOrderItems();
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);
                log.info("{} 님의 {} 번째 주문 중 {} 번째 아이템의 정보 -> name: {}, quantity: {}, price: {}",
                    foundMember, i + 1, j + 1, orderItem.getItem().getName(),
                    orderItem.getQuantity(), orderItem.getPrice());
            }
        }
    }
}
