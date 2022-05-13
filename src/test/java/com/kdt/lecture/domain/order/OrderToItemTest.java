package com.kdt.lecture.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManagerFactory;

@DataJpaTest
class OrderToItemTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("Order <-> OrderItem <-> Item 저장")
    void saveTest() {

    }

    @Test
    @DisplayName("Order <-> OrderItem조회")
    void findTest() {

    }

    @Test
    @DisplayName("Order <-> OrderItem 업데이트")
    void updateTest() {

    }

    @Test
    @DisplayName("Order <-> OrderItem 삭제")
    void deleteTest() {

    }
}