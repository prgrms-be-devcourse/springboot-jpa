package com.kdt.exercise.item.service;

import com.kdt.exercise.domain.order.*;
import com.kdt.exercise.item.dto.ItemDto;
import com.kdt.exercise.item.dto.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(ItemDto itemDto) {
        Item savedItem = itemRepository.save(convertEntity(itemDto));
        return savedItem.getId();
    }

    public ItemDto getItem (Long id) {
        return itemRepository.findById(id)
                .map(this::convertDto)
                .orElseThrow(() -> new NoSuchElementException("아이템을 찾을 수 없습니다."));
    }

    private ItemDto convertDto (Item item) {
        if (item instanceof Food) {
            return ItemDto.builder()
                    .type(ItemType.FOOD)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .chef(((Food) item).getChef())
                    .build();
        }

        if (item instanceof Furniture) {
            return ItemDto.builder()
                    .type(ItemType.FURNITURE)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .width(((Furniture) item).getWidth())
                    .width(((Furniture) item).getHeight())
                    .build();
        }

        if (item instanceof Car) {
            return ItemDto.builder()
                    .type(ItemType.CAR)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .power(((Car) item).getPower())
                    .build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }

    private Item convertEntity(ItemDto itemDto) {
        if (ItemType.FOOD.equals(itemDto.getType())) {
            Food food = new Food();
            food.setPrice(itemDto.getPrice());
            food.setStockQuantity(itemDto.getStockQuantity());
            food.setChef(itemDto.getChef());
            return food;
        }

        if (ItemType.FURNITURE.equals(itemDto.getType())) {
            Furniture furniture = new Furniture();
            furniture.setPrice(itemDto.getPrice());
            furniture.setStockQuantity(itemDto.getStockQuantity());
            furniture.setHeight(itemDto.getHeight());
            furniture.setWidth(itemDto.getWidth());
            return furniture;
        }

        if (ItemType.CAR.equals(itemDto.getType())) {
            Car car = new Car();
            car.setPrice(itemDto.getPrice());
            car.setStockQuantity(itemDto.getStockQuantity());
            car.setPower(itemDto.getPower());
            return car;
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }
}