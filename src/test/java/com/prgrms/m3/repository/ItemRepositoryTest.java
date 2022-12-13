package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Car;
import com.prgrms.m3.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품을 성공적으로 저장할 수 있다.")
    void 저장() {
        //given
        Car car = getCar();
        assertDoesNotThrow(() -> itemRepository.save(car));

        //when
        List<Item> items = itemRepository.findAll();

        //then
        assertEquals(1, items.size());
    }
    @Test
    @DisplayName("상품을 성공적으로 찾을 수 있다.")
    void 조회() {
        //given
        Car car = getCar();
        Car savedCar = itemRepository.save(car);

        //when
        Optional<Item> findResult = itemRepository.findById(savedCar.getId());
        assertTrue(findResult.isPresent());
        Car findCar = (Car) findResult.get();

        //then
        assertEquals(car, findCar);
    }

    @Test
    @DisplayName("상품을 성공적으로 삭제할 수 있다.")
    void 삭제() {
        //given
        Car car = getCar();
        Car savedCar = itemRepository.save(car);

        Optional<Item> findResult = itemRepository.findById(savedCar.getId());
        assertTrue(findResult.isPresent());
        Car findCar = (Car) findResult.get();

        //when
        itemRepository.delete(findCar);
        findResult = itemRepository.findById(savedCar.getId());

        //then
        assertTrue(findResult.isEmpty());
    }

    @Test
    @DisplayName("상품 정보를 변경할 수 있다.")
    void 수정() {
        //given
        Car car = getCar();
        Car savedCar = itemRepository.save(car);

        Optional<Item> findResult = itemRepository.findById(savedCar.getId());
        assertTrue(findResult.isPresent());

        Car findCar = (Car) findResult.get();

        //when
        findCar.removeStock(2);
        itemRepository.save(findCar);

        findResult = itemRepository.findById(savedCar.getId());
        assertTrue(findResult.isPresent());
        Car updatedCar = (Car) findResult.get();

        //then
        assertThat(updatedCar.getStockQuantity()).isEqualTo(3);
    }


    public Car getCar() {
        return new Car(100, 20000, 5);
    }
}