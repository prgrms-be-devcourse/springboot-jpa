package com.devcourse.mission.domain.order.service;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.customer.repository.CustomerRepository;
import com.devcourse.mission.domain.customer.service.CustomerService;
import com.devcourse.mission.domain.item.entity.Item;
import com.devcourse.mission.domain.item.repository.ItemRepository;
import com.devcourse.mission.domain.item.service.ItemService;
import com.devcourse.mission.domain.order.entity.Order;
import com.devcourse.mission.domain.order.repository.OrderRepository;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import com.devcourse.mission.domain.orderitem.repository.OrderItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Customer customer;

    private OrderItem orderItem;

    private Item item;

    @BeforeEach
    void setUpDummyData() {
        long customerId = customerService.save("박현서", "서울", 25);
        customer = customerService.getById(customerId);

        long itemId = itemService.save("책", 10000, 10);
        item = itemService.getById(itemId);

        orderItem = OrderItem.createOrderItem(item, 2);
    }

    @AfterEach
    void clear() {
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문을 저장하면 고객 정보와 상품 주문 정보도 같이 저장한다.")
    void save_order_then_save_customer_and_orderItems() {
        // when
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));
        Order savedOrder = orderService.getDetailOrderById(savedOrderId);

        Customer customerFromOrder = savedOrder.getCustomer();
        OrderItem orderItemFromOrder = savedOrder.getOrderItems().get(0);

        // then
        assertThat(customerFromOrder)
                .hasFieldOrPropertyWithValue("name", "박현서")
                .hasFieldOrPropertyWithValue("address", "서울")
                .hasFieldOrPropertyWithValue("age", 25);

        assertThat(orderItemFromOrder)
                .hasFieldOrPropertyWithValue("totalPrice", 20000)
                .hasFieldOrPropertyWithValue("quantity", 2);
    }

    @Test
    @DisplayName("주문을 저장하면 주문된 상품 재고가 줄어든다.")
    void save_order_then_decrease_item_stock_quantity() {
        // when
        orderService.order(customer.getId(), List.of(orderItem));

        int decreasedItemStockQuantity = itemService.getById(item.getId()).getStockQuantity();

        // then
        assertThat(decreasedItemStockQuantity).isEqualTo(8);
    }

    @Test
    @DisplayName("주문이 삭제됐을 때 모든 주문 상품 목록이 같이 삭제된다.")
    void delete_order_with_orderItems() {
        // given
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));

        // when
        List<Long> orderItemIdsBeforeDelete = orderService.getOrderItemIdsById(savedOrderId);
        orderService.deleteById(savedOrderId);

        // then
        assertThat(orderItemIdsBeforeDelete).isNotEmpty();
        assertThatThrownBy(() -> orderService.getOrderItemIdsById(savedOrderId))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("주문이 삭제됐을 때 모든 주문된 상품 재고가 복구된다.")
    void delete_order_then_increase_item_stock_quantity() {
        // given
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));

        // when
        orderService.deleteById(savedOrderId);

        int increasedItemStockQuantity = itemService.getById(item.getId()).getStockQuantity();

        // then
        assertThat(increasedItemStockQuantity).isEqualTo(10);
    }

    @Test
    @DisplayName("주문된 상품을 추가할 수 있다.")
    void update_add_orderItem() {
        // given
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));
        Order findOrder = orderService.getDetailOrderById(savedOrderId);

        // when
        Item findItem = itemService.getById(item.getId());
        findOrder.addOrderItem(OrderItem.createOrderItem(findItem, 1));

        Order updatedOrder = orderService.getDetailOrderById(savedOrderId);
        OrderItem updatedOrderItems = updatedOrder.getOrderItems().get(0);

        // then
        assertThat(updatedOrderItems)
                .hasFieldOrPropertyWithValue("totalPrice", 30000)
                .hasFieldOrPropertyWithValue("quantity", 3);
    }

    @Test
    @DisplayName("주문된 상품을 삭제할 수 있다.")
    void update_remove_orderItem() {
        // given
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));
        Order findOrder = orderService.getSimpleOrderById(savedOrderId);

        findOrder.addOrderItem(OrderItem.createOrderItem(item, 1));

        // when
        findOrder.removeOrderItemByItemId(item.getId());

        Order updatedOrder = orderService.getDetailOrderById(findOrder.getId());
        List<OrderItem> updatedOrderItems = updatedOrder.getOrderItems();

        // then

        assertThat(updatedOrderItems).isEmpty();
    }

    @Test
    @DisplayName("주문된 상품의 수량을 변경할 수 있다.")
    void update_orderItem_quantity() {
        // given
        long savedOrderId = orderService.order(customer.getId(), List.of(orderItem));
        Order findOrder = orderService.getSimpleOrderById(savedOrderId);
        OrderItem findOrderItem = findOrder.getOrderItems().get(0);

        // when
        findOrderItem.decreaseQuantity(1);

        Order updatedOrder = orderService.getDetailOrderById(savedOrderId);
        OrderItem updatedOrderItems = updatedOrder.getOrderItems().get(0);

        // then
        assertThat(updatedOrderItems)
                .hasFieldOrPropertyWithValue("totalPrice", 10000)
                .hasFieldOrPropertyWithValue("quantity", 1);
    }
}