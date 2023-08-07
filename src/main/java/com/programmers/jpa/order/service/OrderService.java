package com.programmers.jpa.order.service;

import com.programmers.jpa.domain.order.Member;
import com.programmers.jpa.domain.order.Order;
import com.programmers.jpa.domain.order.OrderItem;
import com.programmers.jpa.domain.order.OrderRepository;
import com.programmers.jpa.order.converter.OrderConverter;
import com.programmers.jpa.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public String save(OrderDto dto) {
        List<OrderItem> orderItems = orderConverter.convertOrderItems(dto.orderItemDtos());
        Member member = orderConverter.convertMember(dto.memberDto());
        Order order = Order.createOrder(dto.orderStatus(), dto.memo(), member, orderItems);
        orderItems.forEach(orderItem -> orderItem.attachToOrder(order));

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getUuid();
    }

    public OrderDto findOne(String uuid) throws NotFoundException {
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto)
                .orElseThrow(NotFoundException::new);
    }

    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }
}
