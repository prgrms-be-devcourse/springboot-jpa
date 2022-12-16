package com.devcourse.mission.domain.item.service;

import com.devcourse.mission.domain.item.entity.Item;
import com.devcourse.mission.domain.item.repository.ItemRepository;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import com.devcourse.mission.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public long save(String title, int price, int stockQuantity) {
        Item item = new Item(title, price, stockQuantity);
        return itemRepository.save(item).getId();
    }

    public Item getById(long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품 번호 입니다."));
    }

    @Transactional
    public void deleteById(long itemId) {
        List<OrderItem> findOrderItems = orderItemRepository.joinItemByItemId(itemId);
        if (findOrderItems.isEmpty()) {
            itemRepository.deleteById(itemId);
        }
        throw new IllegalStateException("[ERROR] 해당 상품은 다른 주문들과 관련이 있기 때문에 삭제할 수 없습니다.");
    }
}
