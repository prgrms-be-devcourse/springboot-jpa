package com.example.mission3.repository;

import com.example.mission3.domain.Drink;
import com.example.mission3.domain.Food;
import com.example.mission3.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;


    private Item item;

    @BeforeEach
    @DisplayName("item 추가 - food, drink")
    void insertTest() {
        item = new Item("옷", 4000, 5);
        log.info(item.getId() + ""); // null
        itemRepository.save(item);
        log.info(item.getId() + ""); // 1

        Food food = new Food("라면", 500, 7);
        food.setChef("최현석");
        itemRepository.save(food);

        Drink drink = new Drink("커피", 1500);
        itemRepository.save(drink);

        List<Item> items = itemRepository.findAll();
        assertThat(items.size()).isEqualTo(3);
        assertThat(items.get(0)).isEqualTo(item);

        items.forEach(item1 -> log.info(item1.toString()));
    }

    @Test
    @DisplayName("특정 type의 item을 찾을 수 있다.")
    void findByTypeTest() {
        List<Food> foods = itemRepository.findAllFoodItems();
        assertThat(foods.size()).isEqualTo(1);
        assertThat(foods.get(0).getChef()).isEqualTo("최현석");

        List<Drink> drinks = itemRepository.findAllDrinkItems();
        assertThat(drinks.size()).isEqualTo(1);
        assertThat(drinks.get(0).getName()).isEqualTo("커피");
    }

    @Test
    @DisplayName("item 재고를 update 할 수 있다.")
    void updateStockTest() {
        Optional<Item> findItem = itemRepository.findById(Math.toIntExact(item.getId()));
        assertThat(findItem.isPresent()).isTrue();

        findItem.get().setStock(100);

        Optional<Item> updateItem = itemRepository.findById(Math.toIntExact(item.getId()));
        assertThat(updateItem.isPresent()).isTrue();
        assertThat(updateItem.get().getStock()).isEqualTo(100);
    }

    @Test
    @DisplayName("item을 삭제할 수 있다.")
    void deleteTest() {
        itemRepository.delete(item);

        List<Item> items = itemRepository.findAll();
        assertThat(items.size()).isEqualTo(2);

        Optional<Item> findItem = itemRepository.findById(Math.toIntExact(item.getId()));
        assertThat(findItem.isPresent()).isFalse();
    }

}