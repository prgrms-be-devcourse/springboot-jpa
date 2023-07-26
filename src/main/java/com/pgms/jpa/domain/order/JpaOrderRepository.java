package com.pgms.jpa.domain.order;

import com.pgms.jpa.global.ErrorCode;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager entityManager;

    public JpaOrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long save(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> findOrders = entityManager.createQuery("select o from Order o", Order.class)
                .getResultList();

        return findOrders;
    }

    @Override
    public void deleteById(Long id) {
        Order findOrder = findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NO_SUCH_ELEMENT.getMsg()));

        entityManager.remove(findOrder);
    }
}
