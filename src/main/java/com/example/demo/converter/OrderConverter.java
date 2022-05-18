package com.example.demo.converter;

import com.example.demo.domain.Item;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderStatus;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderConverter {
    public static Order toOrder(OrderDto orderDto) {
        Order order = new Order();

        order.setAddress(orderDto.getAddress());
        order.setPostcode(orderDto.getPostcode());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.toOrderStatus(orderDto.getOrderStatus()));

        order.setCustomer(CustomerConverter.toCustomer(orderDto.getCustomer()));
        order.getCustomer().setOrders(order);
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            order.addOrderItem(OrderConverter.toOrderItem(orderItem));
        }
        return order;
    }

    public static OrderItem toOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setItem(OrderConverter.toItem(orderItemDto.getItem()));
        return orderItem;
    }

    private static Item toItem(ItemDto itemDto) {
        Item item = new Item();

        item.setName(itemDto.getName());
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());
        return item;
    }

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto().builder()
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .orderStatus(order.getOrderStatus().toString())
                .orderDatetime(order.getOrderDatetime())
                .customer(CustomerConverter.toCustomerDto(order.getCustomer()))
                .orderItems(order.getOrderItems().stream().map(OrderConverter::toOrderItemDto).toList())
                .build();
    }

    private static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto().builder()
                .quantity(orderItem.getQuantity())
                .item(OrderConverter.toItemDto(orderItem.getItem()))
                .build();
    }

    private static ItemDto toItemDto(Item item) {
        return new ItemDto().builder()
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
