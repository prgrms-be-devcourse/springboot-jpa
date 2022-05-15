package com.example.demo.converter;

import com.example.demo.domain.*;
import com.example.demo.dto.CustomerDto;
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

        order.setCustomer(OrderConverter.toCustomer(orderDto.getCustomer()));
        order.getCustomer().setOrders(order);

        order.setOrderItems(orderDto.getOrderItems().stream().map(OrderConverter::toOrderItem).toList());
        order.getOrderItems().forEach((e)->{
            e.setOrder(order);
        });
        return order;
    }

    public static Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        System.out.println(customerDto.getEmail());
        return customer;
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
                .customer(OrderConverter.toCustomerDto(order.getCustomer()))
                .orderItems(order.getOrderItems().stream().map(OrderConverter::toOrderItemDto).toList())
                .build();
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto().builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
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
