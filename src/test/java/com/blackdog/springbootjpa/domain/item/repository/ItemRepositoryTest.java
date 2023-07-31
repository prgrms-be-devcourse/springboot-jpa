package com.blackdog.springbootjpa.domain.item.repository;

import com.blackdog.springbootjpa.domain.item.model.Item;
import com.blackdog.springbootjpa.domain.item.vo.OriginNation;
import com.blackdog.springbootjpa.domain.item.vo.Price;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Test
    @DisplayName("아이템을 추가한다.")
    void save_Item_Test() {

        repository.save(item);

        Item result = repository.findById(item.getId()).get();
        assertThat(result.getPrice()).isEqualTo(item.getPrice());
    }

    @Test
    @DisplayName("아이템을 제거한다.")
    void deleteById_ItemId_DeleteEntity() {
        repository.save(item);

        repository.deleteById(item.getId());

        Optional<Item> result = repository.findById(item.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("모든 아이템을 조회한다.")
    void findAll_Void_ReturnItemList() {
        repository.save(item);
        repository.save(newItem);

        List<Item> items = repository.findAll();

        assertThat(items).isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("아이템을 아이디로 조회한다.")
    void findById() {
        Item savedItem = repository.save(item);

        Item result = repository.findById(savedItem.getId()).get();

        assertThat(result.getNation()).isEqualTo(item.getNation());
    }

    private static final Item item = Item.builder()
            .price(new Price(1000))
            .nation(new OriginNation("KR"))
            .build();

    private static final Item newItem = Item.builder()
            .price(new Price(2000))
            .nation(new OriginNation("JP"))
            .build();
}