package com.programmers.jpa.order.converter;

import com.programmers.jpa.domain.order.*;
import com.programmers.jpa.order.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public Member convertMember(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.name())
                .nickName(memberDto.nickName())
                .age(memberDto.age())
                .address(memberDto.address())
                .description(memberDto.description())
                .build();
    }

    public List<OrderItem> convertOrderItems(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream()
                .map(orderItemDto -> OrderItem.builder()
                        .price(orderItemDto.price())
                        .quantity(orderItemDto.quantity())
                        .item(this.convertItem(orderItemDto.itemDto()))
                        .build())
                .toList();
    }

    private Item convertItem(ItemDto itemDto) {
        return switch (itemDto.type()) {
            case FOOD -> Food.builder()
                    .price(itemDto.price())
                    .stockQuantity(itemDto.stockQuantity())
                    .chef(itemDto.chef())
                    .build();
            case FURNITURE -> Furniture.builder()
                    .price(itemDto.price())
                    .stockQuantity(itemDto.stockQuantity())
                    .width(itemDto.width())
                    .height(itemDto.height())
                    .build();
            case CAR -> Car.builder()
                    .price(itemDto.price())
                    .stockQuantity(itemDto.stockQuantity())
                    .power(itemDto.power())
                    .build();
        };
    }

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
        if (item instanceof Food food) {
            return ItemDto.builder()
                    .id(food.getId())
                    .type(ItemType.FOOD)
                    .stockQuantity(food.getStockQuantity())
                    .price(food.getPrice())
                    .chef(food.getChef())
                    .build();
        }

        if (item instanceof Furniture furniture) {
            return ItemDto.builder()
                    .id(furniture.getId())
                    .type(ItemType.FURNITURE)
                    .stockQuantity(furniture.getStockQuantity())
                    .price(furniture.getPrice())
                    .width(furniture.getWidth())
                    .height(furniture.getHeight())
                    .build();
        }

        if (item instanceof Car car) {
            return ItemDto.builder()
                    .id(car.getId())
                    .type(ItemType.CAR)
                    .stockQuantity(car.getStockQuantity())
                    .price(car.getPrice())
                    .power(car.getPower())
                    .build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }
}
