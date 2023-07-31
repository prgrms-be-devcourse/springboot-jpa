package me.kimihiqq.springbootjpa.domain.order;

import me.kimihiqq.springbootjpa.domain.repository.ItemRepository;
import me.kimihiqq.springbootjpa.domain.repository.MemberRepository;
import me.kimihiqq.springbootjpa.domain.repository.OrderItemRepository;
import me.kimihiqq.springbootjpa.domain.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderPersistenceTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Member member;
    private Order order;
    private OrderItem orderItem;
    private Item item;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .name("유재석")
                .nickName("유산슬")
                .age(30)
                .address("서울시 강남구")
                .description("A good member")
                .build();

        order = Order.builder()
                .uuid("123")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memo("This is a test order")
                .build();

        orderItem = OrderItem.builder()
                .price(100)
                .quantity(2)
                .build();

        item = Item.builder()
                .price(50)
                .stockQuantity(100)
                .build();

        member = memberRepository.save(member);
        order = orderRepository.save(order);
        orderItem = orderItemRepository.save(orderItem);
        item = itemRepository.save(item);

        member.addOrder(order);
        order.addOrderItem(orderItem);
        orderItem.addItem(item);
    }

    @AfterEach
    void tearDown() {

        itemRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 양방향관계_저장() {

        assertNotNull(order.getMember());
        assertEquals(1, order.getOrderItems().size());
        assertEquals(1, orderItem.getItems().size());
        assertEquals(member.getName(), order.getMember().getName());
        assertEquals(item.getPrice(), orderItem.getItems().get(0).getPrice());
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {

        Member savedMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        assertFalse(savedMember.getOrders().isEmpty());

        Order savedOrder = savedMember.getOrders().get(0);
        assertFalse(savedOrder.getOrderItems().isEmpty());

        OrderItem savedOrderItem = savedOrder.getOrderItems().get(0);
        assertFalse(savedOrderItem.getItems().isEmpty());
    }
}