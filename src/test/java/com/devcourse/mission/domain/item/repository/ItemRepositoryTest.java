package com.devcourse.mission.domain.item.repository;

import com.devcourse.mission.domain.item.entity.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {ItemRepository.class})
@ActiveProfiles("test")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    void clear() {
        itemRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품 저장에 성공한다.")
    void save_item() {
        // given
        Item item = new Item("책", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        assertThat(savedItem)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(item);
    }

    @Test
    @DisplayName("상품 검색에 성공한다.")
    void find_item_by_id() {
        // given
        Item item = new Item("책", 10000, 10);
        itemRepository.saveAndFlush(item);

        // when
        Item findItem = itemRepository.findById(item.getId()).get();

        // then
        assertThat(findItem)
                .usingRecursiveComparison()
                .isEqualTo(item);
    }

    @Test
    @DisplayName("상품 수정에 성공한다.")
    void update_item() {
        // given
        Item item = new Item("책", 10000, 10);
        Item savedItem = itemRepository.saveAndFlush(item);

        // when
        savedItem.changeTitle("스프링 책");
        savedItem.changePrice(10000);
        savedItem.increaseQuantity(20);

        // then
        assertThat(savedItem)
                .hasFieldOrPropertyWithValue("title", "스프링 책")
                .hasFieldOrPropertyWithValue("price", 10000)
                .hasFieldOrPropertyWithValue("stockQuantity", 30);
    }

    @Test
    @DisplayName("상품 삭제에 성공한다.")
    void delete_item() {
        // given
        Item item = new Item("책", 10000, 10);
        Item savedItem = itemRepository.saveAndFlush(item);

        // when
        itemRepository.deleteById(savedItem.getId());

        // then
        assertThatThrownBy(() -> itemRepository.findById(savedItem.getId()).get())
                .isExactlyInstanceOf(NoSuchElementException.class);
    }
}