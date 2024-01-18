package com.example.kdtjpa.domain.order.order.converter;

import com.example.kdtjpa.domain.order.*;
import com.example.kdtjpa.domain.order.dto.ItemDto;
import com.example.kdtjpa.domain.order.dto.ItemType;
import com.example.kdtjpa.domain.order.dto.MemberDto;
import com.example.kdtjpa.domain.order.order.dto.OrderDto;
import com.example.kdtjpa.domain.order.order.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    public Order convertOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .createdBy(orderDto.getMemberDto().getName())
                .memo("---").build();

        order.setMember(this.convertMember(orderDto.getMemberDto()));
        this.convertOrderItems(orderDto).forEach(order::addOrderItem);

        return order;
    }

    private Member convertMember(MemberDto memberDto) {
        Member member = Member.builder()
                .name("parkeugene")
                .nickName("보그리")
                .address("뽀글")
                .age(23)
                .description("대학생임다")
                .createdBy("eugene")
                .build();

        return member;
    }

    private List<OrderItem> convertOrderItems(OrderDto orderDto) {
        return orderDto.getOrderItemDtos().stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = OrderItem.builder()
                            .price(orderItemDto.getPrice())
                            .quantity(orderItemDto.getQuantity()).build();
                    List<Item> items = orderItemDto.getItemDtos().stream()
                            .map(this::convertItem)
                            .toList();
                    items.forEach(orderItem::addItem);
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    private Item convertItem(ItemDto itemDto) {
        if (ItemType.FOOD.equals(itemDto.getType())) {
            return Food.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .chef(itemDto.getChef()).build();
        }

        if (ItemType.FURNITURE.equals(itemDto.getType())) {
            return Furniture.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .width(itemDto.getWidth())
                    .height(itemDto.getHeight()).build();
        }

        if (ItemType.CAR.equals(itemDto.getType())) {
            return Car.builder()
                    .price(itemDto.getPrice())
                    .stockQuantity(itemDto.getStockQuantity())
                    .power(itemDto.getPower()).build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }


    public OrderDto convertOrderDto(Order order) {
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


    private MemberDto convertMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .description(member.getDescription())
                .age(member.getAge())
                .address(member.getAddress())
                .build();
    }

    private OrderItemDto convertOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .itemDtos(orderItem.getItems().stream()
                        .map(this::convertItemDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private ItemDto convertItemDto(Item item) {
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
