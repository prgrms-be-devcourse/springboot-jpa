package com.example.weeklyjpa.service;

import com.example.weeklyjpa.domain.item.Item;
import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.domain.order.Order;
import com.example.weeklyjpa.domain.order.OrderItem;
import com.example.weeklyjpa.repository.ItemRepository;
import com.example.weeklyjpa.repository.MemberRepository;
import com.example.weeklyjpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public static final String memo = "경비실 앞에 놔주세요";

    @Transactional
    public Long createOrder(Long memberId, Long itemId, int orderQuantity) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        OrderItem orderItem = OrderItem.createOrderItem(item, orderQuantity);
        Order order = Order.createOrder(memo, member, orderItem);

        return orderRepository.save(order).getId();
    }

    public Order findOne(Long orderId) {

        return  orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("주문 정보가 없습니다."));
    }

}
