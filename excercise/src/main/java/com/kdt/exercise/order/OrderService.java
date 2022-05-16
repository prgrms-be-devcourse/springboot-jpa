package com.kdt.exercise.order;

import com.kdt.exercise.domain.order.Order;
import com.kdt.exercise.domain.order.OrderRepository;
import com.kdt.exercise.order.converter.OrderConverter;
import com.kdt.exercise.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
        // 2. orderRepository.save(enitiy) -> 영속화
        Order entity = orderRepository.save(order);
        // 3. 결과 반환
        return entity.getUuid();
    }

    @Transactional
    public OrderDto findOne(String uuid) throws NoSuchElementException {
        // 1. 조회를 위한 키값 인자로 받기
        // 2. orderRepository.findById(uuid) -> 조회 (영속화된 엔티티)
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto) // 3. entity -> dto
                .orElseThrow(() -> new NoSuchElementException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }

    @Transactional
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}