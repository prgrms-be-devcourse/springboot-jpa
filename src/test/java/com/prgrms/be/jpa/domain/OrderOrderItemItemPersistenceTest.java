package com.prgrms.be.jpa.domain;

import com.prgrms.be.jpa.repository.ItemRepository;
import com.prgrms.be.jpa.repository.MemberRepository;
import com.prgrms.be.jpa.repository.OrderItemRepository;
import com.prgrms.be.jpa.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.be.jpa.domain.OrderStatus.OPENED;

@Slf4j
@DataJpaTest
public class OrderOrderItemItemPersistenceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("주문과 주문아이템 간의 관계는 1:N 관계이다.")
    void checkOneToManyOrderTest() {
        // given
        Member member = new Member("이수영", "항시영", 25, "대구광역시 남구", "백엔드 개발지망생입니다.");
        memberRepository.save(member);
        Item item = new Car(1000, 10, 100);
        itemRepository.save(item);

        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OPENED, "백엔드 재밌음.", member);
        OrderItem orderItem1 = new OrderItem(1000, 1, order, item);
        OrderItem orderItem2 = new OrderItem(1000, 1, order, item);

        // when
        Order getOrder = orderRepository.save(order);
        Optional<Order> updatedOrder = orderRepository.findById(order.getUuid());
        Assertions.assertThat(updatedOrder).isPresent();

        // then
        Assertions.assertThat(updatedOrder.get().getOrderItems())
                .hasSize(2)
                .isSameAs(getOrder.getOrderItems());
    }
}
