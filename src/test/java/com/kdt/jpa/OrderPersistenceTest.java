package com.kdt.jpa;

import com.kdt.jpa.entity.Customer;
import com.kdt.jpa.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kdt.jpa.type.OrderStatus.OPENED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringBootTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    void 양방향관계_저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Orders order = Orders.builder()
                .address("서울")
                .orderStatus(OPENED)
                .build();
        entityManager.persist(order);

        Customer customer = new Customer("first", "last");
        customer.getOrderList()
                .add(order);

        customer.getOrderList()
                .add(order);

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        Orders findOrder = entityManager.find(Orders.class, order.getId());
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        transaction.commit();

        assertThat(findOrder, in(findCustomer.getOrderList()));
        assertThat(findOrder, samePropertyValuesAs(order));
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    void 양방향관계_저장_편의메서드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Orders order = Orders.builder()
                .address("서울")
                .orderStatus(OPENED)
                .build();
        entityManager.persist(order);

        Customer customer = new Customer("first", "last");
        customer.getOrderList()
                .add(order);

        customer.addOrder(order);

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        Orders findOrder = entityManager.find(Orders.class, order.getId());
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        transaction.commit();

        assertThat(findOrder, in(findCustomer.getOrderList()));
        assertThat(findOrder, samePropertyValuesAs(order));
        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    void 객체그래프탐색() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Orders order = Orders.builder()
                .address("서울")
                .orderStatus(OPENED)
                .build();
        entityManager.persist(order);

        Customer customer = new Customer("first", "last");
        customer.addOrder(order);

        entityManager.persist(customer);
        transaction.commit();
        entityManager.clear();

        transaction.begin();
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        transaction.commit();

        Assertions.assertThat(order).usingRecursiveComparison().isEqualTo(findCustomer.getOrderList().get(0));
    }
}
