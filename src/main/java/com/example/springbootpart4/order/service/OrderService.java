package com.example.springbootpart4.order.service;

import com.example.springbootpart4.domain.order.Order;
import com.example.springbootpart4.domain.order.OrderRepository;
import com.example.springbootpart4.order.converter.OrderConverter;
import com.example.springbootpart4.order.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javassist.NotFoundException;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Transactional
    public String save(OrderDto dto) {
        // 1. dto -> entity 변환 (준영속)
        Order order = orderConverter.convertOrder(dto);

        // 2. orderRepository.save() (영속화)
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
    public OrderDto findOne(String uuid) throws NotFoundException {
        // 1. 조회를 위한 키 값 받기
        // 2. orderRepository.findById(uuid) -> 조회 (영속화된 엔티티)
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto)  // 3. entity -> dto
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
    }
}
