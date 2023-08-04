package com.programmers.springbootjpa.domain.mission3.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private Car car;
    private Food food;
    private Furniture furniture;

    @BeforeEach
    void setUp() {
        car = new Car(10, 20, 30);
        food = new Food(30, 5, "chef");
        furniture = new Furniture(20, 5, 10, 20);
    }

    @DisplayName("상품을 저장한다")
    @Test
    void testSave() {
        //given
        //when
        Car savedCar = itemRepository.save(car);
        Car result = (Car) itemRepository.findById(savedCar.getId()).get();

        //then
        assertThat(result.getPrice()).isEqualTo(car.getPrice());
        assertThat(result.getStockQuantity()).isEqualTo(car.getStockQuantity());
    }

    @DisplayName("상품을 수정한다")
    @Test
    void testUpdate() {
        //given
        Car savedCar = itemRepository.save(car);

        //when
        savedCar.update(222, 22, 2);

        //then
        assertThat(savedCar.getPrice()).isEqualTo(222);
        assertThat(savedCar.getStockQuantity()).isEqualTo(22);
        assertThat(savedCar.getPower()).isEqualTo(2);
    }

    @DisplayName("상품을 id로 조회한다")
    @Test
    void testFindById() {
        //given
        Food savedFood = itemRepository.save(food);

        //when
        Food result = (Food) itemRepository.findById(savedFood.getId()).get();

        //then
        assertThat(result).isInstanceOf(Food.class);
        assertThat(result.getPrice()).isEqualTo(savedFood.getPrice());
        assertThat(result.getStockQuantity()).isEqualTo(savedFood.getStockQuantity());
        assertThat(result.getChef()).isEqualTo(savedFood.getChef());
    }

    @DisplayName("저장된 상품을 모두 조회한다")
    @Test
    void testFindAll() {
        //given
        itemRepository.save(car);
        itemRepository.save(food);
        itemRepository.save(furniture);

        //when
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items).hasSize(3);
    }

    @DisplayName("주문을 삭제한다")
    @Test
    void testDelete() {
        //given
        Furniture savedFurniture = itemRepository.save(furniture);

        //when
        itemRepository.delete(savedFurniture);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items).isEmpty();
    }

    @DisplayName("저장된 주문을 모두 삭제한다")
    @Test
    void testDeleteAll() {
        //given
        itemRepository.save(car);
        itemRepository.save(food);
        itemRepository.save(furniture);

        //when
        itemRepository.deleteAll();
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items).isEmpty();
    }
}