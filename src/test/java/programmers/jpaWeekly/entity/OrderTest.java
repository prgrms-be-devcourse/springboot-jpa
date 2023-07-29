package programmers.jpaWeekly.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import programmers.jpaWeekly.item.entity.Item;
import programmers.jpaWeekly.item.repository.ItemRepository;
import programmers.jpaWeekly.order.entity.Order;
import programmers.jpaWeekly.order.entity.OrderItem;
import programmers.jpaWeekly.order.repository.OrderItemRepository;
import programmers.jpaWeekly.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    public Item createItem() {
        return new Item("testItem", 2000, 50);
    }

    @AfterEach
    void deleteAll() {
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
    }

    @Test
    void createOrderItemTest() {
        Item item = createItem();
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(item, 3);
        orderItemRepository.save(orderItem);

        OrderItem foundOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        Item foundItem = itemRepository.findById(item.getId()).get();

        assertThat(foundOrderItem.getItem()).isEqualTo(foundItem);
    }

    @Test
    void createOrderTest() {
        Item item = createItem();
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(item, 3);
        orderItemRepository.save(orderItem);

        Order order = new Order();
        orderItem.setOrder(order);

        Order saveOrder = orderRepository.save(order);

        assertThat(saveOrder.getOrderItemList().get(0)).isEqualTo(orderItem);
        assertThat(orderItem.getOrder()).isEqualTo(order);
    }
}