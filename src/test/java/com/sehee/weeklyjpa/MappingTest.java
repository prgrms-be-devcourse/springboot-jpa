package com.sehee.weeklyjpa;

import com.sehee.weeklyjpa.domain.parent.Parent;
import com.sehee.weeklyjpa.domain.parent.Parent2;
import com.sehee.weeklyjpa.domain.parent.ParentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MappingTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;


    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @Test
    @DisplayName("IdClass로 다중 식별자 구현")
    void idClassTest() {
        transaction.begin();
        Parent parent = new Parent();
        parent.setId1("id1");
        parent.setId2("id2");
        entityManager.persist(parent);
        transaction.commit();

        Parent retrievedEntity = entityManager.find(Parent.class, new ParentId(parent.getId1(), parent.getId2()));
        log.info("{} {}", retrievedEntity.getId1(), retrievedEntity.getId2());
    }

    @Test
    @DisplayName("EmbeddedId로 다중 식별자 구현")
    void embeddedIdTest() {
        transaction.begin();
        Parent2 parent2 = new Parent2();
        parent2.setId(new ParentId("id1", "id2"));
        entityManager.persist(parent2);
        transaction.commit();

        Parent2 retrievedEntity = entityManager.find(Parent2.class, parent2.getId());
        ParentId parentId = retrievedEntity.getId();
        log.info("{} {}", parentId.getId1(), parentId.getId2());
    }
}
