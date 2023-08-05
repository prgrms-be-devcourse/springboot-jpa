package com.programmers.jpa.item.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private EntityManagerFactory emf;

    @DisplayName("자동차 상품을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 1000",
            "2000, 30, 2000"
    })
    void createCar(int price, int stockQuantity, int power) {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Item car = Car.of(price, stockQuantity, power);
        em.persist(car);
        transaction.commit();

        //when
        final long itemId = car.getId();

        em.clear();
        Item foundCar = em.find(Item.class, itemId);

        //then
        final String expectedItemType = "CAR";

        assertThat(foundCar.getPower()).isEqualTo(power);
        assertThat(foundCar.getPrice()).isEqualTo(price);
        assertThat(foundCar.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(foundCar.getId()).isEqualTo(itemId);
        assertThat(foundCar.getItemType()).isEqualTo(expectedItemType);
    }

    @DisplayName("자동차 상품에서 다른 상품의 기능을 사용하면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 1000",
            "2000, 30, 2000"
    })
    void throwExceptionWhenUsingDifferentFeaturesInCarItem(int price, int stockQuantity, int power) {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Item car = Car.of(price, stockQuantity, power);
        em.persist(car);
        transaction.commit();

        //when, then
        final long itemId = car.getId();

        em.clear();
        Item foundCar = em.find(Item.class, itemId);

        assertThatThrownBy(()->foundCar.getChef())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품은 음식이 아닙니다.");
    }

    @DisplayName("음식 상품을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 유명한",
            "2000, 30, 박은지"
    })
    void createFood(int price, int stockQuantity, String chef) {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Item food = Food.of(price, stockQuantity, chef);
        em.persist(food);
        transaction.commit();

        //when
        final long itemId = food.getId();

        em.clear();
        Item foundFood = em.find(Item.class, itemId);

        //then
        final String expectedItemType = "FOOD";

        assertThat(foundFood.getChef()).isEqualTo(chef);
        assertThat(foundFood.getPrice()).isEqualTo(price);
        assertThat(foundFood.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(foundFood.getId()).isEqualTo(itemId);
        assertThat(foundFood.getItemType()).isEqualTo(expectedItemType);
    }

    @DisplayName("음식 상품에서 다른 상품의 기능을 사용하면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 유명한",
            "2000, 30, 박은지"
    })
    void throwExceptionWhenUsingDifferentFeaturesInFoodItem(int price, int stockQuantity, String chef) {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Item food = Food.of(price, stockQuantity, chef);
        em.persist(food);
        transaction.commit();

        //when, then
        final long itemId = food.getId();

        em.clear();
        Item foundFood = em.find(Item.class, itemId);

        assertThatThrownBy(()->foundFood.getPower())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품은 자동차가 아닙니다.");
    }
}
