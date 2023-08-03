package com.programmers.jpa.order.converter;

import com.programmers.jpa.domain.order.*;
import com.programmers.jpa.order.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    // dto -> entity
    public Order convertOrder(OrderDto orderDto) {
        List<OrderItem> orderItems = this.convertOrderItems(orderDto.getOrderItemDtos());

        Order order = Order.builder()
                .orderStatus(orderDto.getOrderStatus())
                .memo(orderDto.getMemo())
                .member(this.convertMember(orderDto.getMemberDto()))
                .orderItems(orderItems)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return order;
    }

    private Member convertMember(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .nickName(memberDto.getNickName())
                .age(memberDto.getAge())
                .address(memberDto.getAddress())
                .description(memberDto.getDescription())
                .build();
    }

    private List<OrderItem> convertOrderItems(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream()
                .map(orderItemDto -> OrderItem.builder()
                        .price(orderItemDto.getPrice())
                        .quantity(orderItemDto.getQuantity())
                        .item(this.convertItem(orderItemDto.getItemDto()))
                        .build())
                .toList();
    }

    private Item convertItem(ItemDto itemDto) {
        if (ItemType.FOOD.equals(itemDto.getType())) {
            return Food.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .chef(itemDto.getChef())
                    .build();
        }

        if (ItemType.FURNITURE.equals(itemDto.getType())) {
            return Furniture.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .width(itemDto.getWidth())
                    .height(itemDto.getHeight())
                    .build();
        }

        if (ItemType.CAR.equals(itemDto.getType())) {
            return Car.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .power(itemDto.getPower())
                    .build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }


    // entity -> dto
    public OrderDto convertOrderDto (Order order) {
        return OrderDto.builder()
                .uuid(order.getUuid())
                .memo(order.getMemo())
                .orderStatus(order.getOrderStatus())
                .orderDatetime(order.getOrderDatetime())
                .memberDto(this.convertMemberDto(order.getMember()))
                .orderItemDtos(order.getOrderItems().stream()
                        .map(this::convertOrderItemDto)
                        .collect(Collectors.toList())
                )
                .build();
    }


    private MemberDto convertMemberDto (Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .description(member.getDescription())
                .age(member.getAge())
                .address(member.getAddress())
                .build();
    }

    private OrderItemDto convertOrderItemDto (OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .itemDto(convertItemDto(orderItem.getItem()))
                .build();
    }

    private ItemDto convertItemDto (Item item) {
        if (item instanceof Food) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.FOOD)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .chef(((Food) item).getChef())
                    .build();
        }

        if (item instanceof Furniture) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.FURNITURE)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .width(((Furniture) item).getWidth())
                    .width(((Furniture) item).getHeight())
                    .build();
        }

        if (item instanceof Car) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.CAR)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .power(((Car) item).getPower())
                    .build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }
}
