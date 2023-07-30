package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.item.domain.Car;
import com.programmers.week.item.domain.Food;
import com.programmers.week.item.domain.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@ContextConfiguration(classes = WeekApplication.class)
public class ItemTest {

  private static final long ID = 1L;

  @Autowired
  EntityManagerFactory emf;

  @ParameterizedTest
  @CsvSource(value = {"1000|10|1000", "4000|4|6000", "2500|30|2500"}, delimiter = '|' )
  void create_Car_Success(int price, int quantity, long power) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Item item = Car.of(price, quantity, power);
    em.persist(item);
    transaction.commit();
    Item car = em.find(Car.class, ID);

    log.info("자동차 아이템 {} price: {} quantity: {}", car, car.getPrice(), car.getStockQuantity());
    assertEquals(price, car.getPrice());
    assertEquals(quantity, car.getStockQuantity());
  }

  @ParameterizedTest
  @CsvSource(value = {"1000|10|박은지", "4000|4|유명한", "2500|30|고범준"}, delimiter = '|' )
  void create_Food_Success(int price, int quantity, String chef) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Item item = Food.of(price, quantity, chef);
    em.persist(item);
    transaction.commit();
    Item food = em.find(Item.class, ID);

    log.info("음식 아이템 {} price: {} quantity: {}", food, food.getPrice(), food.getStockQuantity());
    assertEquals(price, food.getPrice());
    assertEquals(quantity, food.getStockQuantity());
  }

  @ParameterizedTest
  @CsvSource(value = {"1000|-1|1000", "-2000|4|6000", "-5000|-30|2500"}, delimiter = '|' )
  @DisplayName("올바르지 않은 가격과 수량, 파워를 입력하면 상품을 생성할 수 없다.")
  void create_Car_Failure(int price, int quantity, long power) {
    assertThatThrownBy(() -> Car.of(price, quantity, power))
            .isInstanceOf(IllegalArgumentException.class);
  }

}
