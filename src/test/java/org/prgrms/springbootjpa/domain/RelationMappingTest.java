package org.prgrms.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelationMappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void order_orderItems_관계_저장(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Customer customer = new Customer("seungeun", "kim");
        customer.setAddress("경기도 화성시 영통로");
        customer.setAge(23);
        customer.setNickname("빈푸");

        Product product = new Product(60000, 3);
        OrderItem orderItem = new OrderItem(product, product.getPrice(), 2);
        Order order = new Order(UUID.randomUUID().toString(), OrderStatus.ACCEPTED, customer);

        transaction.begin();

        em.persist(product);
        em.persist(customer);
        em.persist(order);
        em.persist(orderItem);
        order.addOrderItem(orderItem);

        transaction.commit();

        // Order에 속하는 OrderItem 조회
        Order orderResult = em.find(Order.class, order.getId());
        assertThat(orderResult.getOrderItems().get(0).getId()).isEqualTo(orderItem.getId());

        transaction.begin();
        em.remove(product);
        em.remove(customer);
        em.remove(order);
        em.remove(orderItem);
        transaction.commit();
    }

    @Test
    void product_orderItems_관계_저장(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Customer customer = new Customer("seungeun", "kim");
        customer.setAddress("경기도 화성시 영통로");
        customer.setAge(23);
        customer.setNickname("빈푸");

        Product product = new Product(60000, 3);
        OrderItem orderItem = new OrderItem(product, product.getPrice(), 2);
        Order order = new Order(UUID.randomUUID().toString(), OrderStatus.ACCEPTED, customer);

        transaction.begin();
        em.persist(product);
        em.persist(customer);
        em.persist(order);
        em.persist(orderItem);
        product.addProductOrder(orderItem);
        transaction.commit();

        // Product을 주문한 orderItem 조회
        Product productResult = em.find(Product.class, product.getId());
        assertThat(productResult.getProductOrders().get(0).getId()).isEqualTo(orderItem.getId());

        transaction.begin();
        em.remove(product);
        em.remove(customer);
        em.remove(order);
        em.remove(orderItem);
        transaction.commit();
    }

    @Test
    void customer_order_관계_저장(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Customer customer = new Customer("seungeun", "kim");
        customer.setAddress("경기도 화성시 영통로");
        customer.setAge(23);
        customer.setNickname("빈푸");

        Product product = new Product(60000, 3);
        OrderItem orderItem = new OrderItem(product, product.getPrice(), 2);
        Order order = new Order(UUID.randomUUID().toString(), OrderStatus.ACCEPTED, customer);

        transaction.begin();
        em.persist(product);
        em.persist(customer);
        em.persist(order);
        em.persist(orderItem);
        customer.addOrder(order);
        transaction.commit();

        // customer가 주문한 order 조회
        Customer customerResult = em.find(Customer.class, customer.getId());
        assertThat(customerResult.getOrders().get(0).getId()).isEqualTo(order.getId());

        transaction.begin();
        em.remove(product);
        em.remove(customer);
        em.remove(order);
        em.remove(orderItem);
        transaction.commit();
    }

}

