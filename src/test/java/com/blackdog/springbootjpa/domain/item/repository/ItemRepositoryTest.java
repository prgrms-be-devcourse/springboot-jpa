package com.blackdog.springbootjpa.domain.item.repository;

import com.blackdog.springbootjpa.domain.item.model.Item;
import global.annotation.JpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    private final Item item = ItemTestData.buildItem();
    private final Item newItem = ItemTestData.buildNewItem();

    @Test
    @DisplayName("아이템을 추가한다.")
    void save_Item_Test() {
        //when
        repository.save(item);

        //then
        Item result = repository.findById(item.getId()).get();

        assertThat(result.getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    @DisplayName("아이템을 제거한다.")
    void deleteById_ItemId_DeleteEntity() {
        // given
        repository.save(item);

        // when
        repository.deleteById(item.getId());

        // then
        Optional<Item> result = repository.findById(item.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("모든 아이템을 조회한다.")
    void findAll_Void_ReturnItemList() {
        // given
        repository.save(item);
        repository.save(newItem);

        // when
        List<Item> items = repository.findAll();

        // then
        assertThat(items).isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("아이템을 아이디로 조회한다.")
    void findById() {
        // given
        Item savedItem = repository.save(item);

        // when
        Item result = repository.findById(savedItem.getId()).get();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedItem);
    }

}
