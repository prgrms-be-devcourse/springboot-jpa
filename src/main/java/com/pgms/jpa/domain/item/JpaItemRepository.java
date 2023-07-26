package com.pgms.jpa.domain.item;

import com.pgms.jpa.global.ErrorCode;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
public class JpaItemRepository implements ItemRepository {

    private final EntityManager entityManager;

    public JpaItemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long save(Item item) {
        entityManager.persist(item);
        return item.getId();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item findItem = entityManager.find(Item.class, id);
        return Optional.ofNullable(findItem);
    }

    @Override
    public List<Item> findAll() {
        List<Item> findItems = entityManager.createQuery("select i from Item i", Item.class)
                .getResultList();

        return findItems;
    }

    @Override
    public void deleteById(Long id) {
        Item findItem = findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NO_SUCH_ELEMENT.getMsg()));

        entityManager.remove(findItem);
    }
}
