package com.prgrms.m3.service;

import com.prgrms.m3.domain.Item;
import com.prgrms.m3.domain.Member;
import com.prgrms.m3.domain.Order;
import com.prgrms.m3.domain.OrderItem;
import com.prgrms.m3.repository.ItemRepository;
import com.prgrms.m3.repository.MemberRepository;
import com.prgrms.m3.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long order(Long memberId, Long itemId, int count) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        OrderItem orderItem = OrderItem.createOrderItem(findItem, count);

        Order order = Order.createOrder(findMember, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 주문번호입니다."));

        order.cancelOrder();

        orderRepository.delete(order);
    }
}
