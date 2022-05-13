package com.kdt.jpa.domain.order.service;

import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.OrderRepository;
import com.kdt.jpa.domain.order.converter.OrderConverter;
import com.kdt.jpa.domain.order.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public OrderService(OrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    public String save(OrderDto orderDto) {

        // dto => entity 변환 ( 준영속 상태 )
        Order order = orderConverter.convertOrder(orderDto);

        // save
        Order save = orderRepository.save(order);

        // return
        return save.getOrderId();
    }

    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }


    public OrderDto findOne(String orderId) {

       // 1 . 조회 하기 위한 키 값을 인자로 받는다.
        Optional<Order> byId = orderRepository.findById(orderId);
        // 2. 조회 ( 영속화된 엔티티)
        return orderRepository.findById(orderId)
                .map(orderConverter::convertOrderDto)
                .orElseThrow(RuntimeException::new);

        // 3. entity -> dto
    }



}
