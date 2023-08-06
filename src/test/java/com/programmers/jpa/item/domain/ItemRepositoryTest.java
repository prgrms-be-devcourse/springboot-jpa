package com.programmers.jpa.item.domain;

import com.programmers.jpa.item.infra.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository<Item> itemRepository;

    @DisplayName("자동차 상품을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 1000",
            "2000, 30, 2000"
    })
    void createCar(int price, int stockQuantity, int power) {
        //given
        Car car = Car.of(price, stockQuantity, power);

        //when
        Car savedCar = itemRepository.save(car);

        //then
        assertThat(savedCar.getPower()).isEqualTo(power);
        assertThat(savedCar.getPrice()).isEqualTo(price);
        assertThat(savedCar.getStockQuantity()).isEqualTo(stockQuantity);
    }

    @DisplayName("전체 음식 상품을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1000, 10, 유명한",
            "2000, 30, 박은지"
    })
    void createFood(int price, int stockQuantity, String chef) {
        //given
        Food food = Food.of(price, stockQuantity, chef);

        //when
        Food savedFood = itemRepository.save(food);

        //then
        assertThat(savedFood.getChef()).isEqualTo(chef);
        assertThat(savedFood.getPrice()).isEqualTo(price);
        assertThat(savedFood.getStockQuantity()).isEqualTo(stockQuantity);
    }

    @DisplayName("상품 목록을 타입별 조회할 수 있다.")
    @Test
    void findAllCar() {
        //given
        final int price = 1000;
        final int stockQuantity = 10;
        final int power = 100;
        final String chef = "chef";

        Car car1 = Car.of(price, stockQuantity, power);
        Car car2 = Car.of(price, stockQuantity, power);
        List<Car> carList = List.of(car1, car2);

        Food food1 = Food.of(price, stockQuantity, chef);
        Food food2 = Food.of(price, stockQuantity, chef);
        List<Food> foodList = List.of(food1, food2);

        itemRepository.saveAll(foodList);
        itemRepository.saveAll(carList);

        //when
        List<Item> foundFoodList = itemRepository.findAllByItemType("FOOD");
        List<Item> foundCarList = itemRepository.findAllByItemType("CAR");

        //then
        final int expectedSize = 2;
        assertThat(foundCarList).hasSize(expectedSize);
        assertThat(foundFoodList).hasSize(expectedSize);
    }
}
