package com.example.kdtjpa.domain.order.order.service;

import com.example.kdtjpa.domain.order.Order;
import com.example.kdtjpa.domain.order.OrderRepository;
import com.example.kdtjpa.domain.order.order.dto.OrderDto;
import com.example.kdtjpa.domain.order.order.converter.OrderConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    @Transactional  // 영속성 관리
    public String save(OrderDto dto) {
        // 1. dto -> entity 변환 (준영속)
        Order order = orderConverter.convertOrder(dto);
        // 2. orderRepository.save(entity) -> 영속화
        Order entity = orderRepository.save(order);
        // 3. 결과 반환
        return entity.getUuid();
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }

    @Transactional
    public OrderDto findOne(String uuid) {
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));
    }
}
