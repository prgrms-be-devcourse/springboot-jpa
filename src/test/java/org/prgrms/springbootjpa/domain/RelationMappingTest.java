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

    EntityTransaction transaction;

    EntityManager em;

    private Customer customer;
    private Product product;
    private OrderItem orderItem;
    private Order order;

    @BeforeEach
    void setUp(){
        em = emf.createEntityManager();
        transaction = em.getTransaction();

        customer = new Customer("seungeun", "kim");
        customer.setAddress("경기도 화성시 영통로");
        customer.setAge(23);
        customer.setNickname("빈푸");

        product = new Product(60000, 3);
        orderItem = new OrderItem(product, product.getPrice(), 2);
        order = new Order(UUID.randomUUID().toString(), OrderStatus.ACCEPTED, customer);

        transaction.begin();
        em.persist(product);
        em.persist(customer);
        em.persist(order);
        em.persist(orderItem);
        transaction.commit();
    }

    @AfterEach
    void tearDown(){
        transaction.begin();
        em.remove(product);
        em.remove(customer);
        em.remove(order);
        em.remove(orderItem);
        transaction.commit();
    }

    @Test
    void order_orderItems_관계_저장(){
        transaction.begin();
        order.addOrderItem(orderItem);
        transaction.commit();

        // Order에 속하는 OrderItem 조회
        Order orderResult = em.find(Order.class, order.getId());
        assertThat(orderResult.getOrderItems().get(0).getId()).isEqualTo(orderItem.getId());
    }

    @Test
    void product_orderItems_관계_저장(){
        transaction.begin();
        product.addProductOrder(orderItem);
        transaction.commit();

        // Product을 주문한 orderItem 조회
        Product productResult = em.find(Product.class, product.getId());
        assertThat(productResult.getProductOrders().get(0).getId()).isEqualTo(orderItem.getId());
    }

    @Test
    void customer_order_관계_저장(){
        transaction.begin();
        customer.addOrder(order);
        transaction.commit();

        // customer가 주문한 order 조회
        Customer customerResult = em.find(Customer.class, customer.getId());
        assertThat(customerResult.getOrders().get(0).getId()).isEqualTo(order.getId());
    }

}

