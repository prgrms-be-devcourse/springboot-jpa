package com.kdt.jpa.domain.order.converter;

import com.kdt.jpa.domain.item.Car;
import com.kdt.jpa.domain.item.Food;
import com.kdt.jpa.domain.item.Furniture;
import com.kdt.jpa.domain.item.Item;
import com.kdt.jpa.domain.item.dto.ItemDto;
import com.kdt.jpa.domain.item.dto.ItemType;
import com.kdt.jpa.domain.member.Member;
import com.kdt.jpa.domain.member.dto.MemberDto;
import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.OrderItem;
import com.kdt.jpa.domain.order.dto.OrderDto;
import com.kdt.jpa.domain.order.dto.OrderItemDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    public Order convertOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setMemo(orderDto.getMemo());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setOrderDatetime(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(orderDto.getMemberDto().getName());

        order.setMember(this.convertMember(orderDto.getMemberDto()));
        this.convertOrderItems(orderDto).forEach(order::addOrderItem);

        return order;
    }

    public OrderDto convertOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .memo(order.getMemo())
                .orderStatus(order.getOrderStatus())
                .orderDatetime(order.getOrderDatetime())
                .memberDto(convertMemberDto(order.getMember()))
                .orderItemDtos(order.getOrderItems().stream()
                        .map(item -> convertOrderItemDto(item))
                        .collect(Collectors.toList()))
                .build();
    }

    private MemberDto convertMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickName(member.getNickname())
                .address(member.getAddress())
                .age(member.getAge())
                .description(member.getDescription())
                .build();
    }

    private OrderItemDto convertOrderItemDto(OrderItem item) {
        return OrderItemDto.builder()
                .id(item.getId())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .itemDtos(item.getItems().stream()
                        .map(this::convertItemDto)
                        .collect(Collectors.toList()))
                        .build();
    }


    private Member convertMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setNickname(memberDto.getNickName());
        member.setAge(memberDto.getAge());
        member.setAddress(memberDto.getAddress());
        member.setDescription(memberDto.getDescription());

        return member;
    }


    private List<OrderItem> convertOrderItems(OrderDto orderDto) {
        return orderDto.getOrderItemDtos().stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setPrice(orderItemDto.getPrice());
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    List<Item> items = orderItemDto.getItemDtos().stream()
                            .map(this::convertItem)
                            .collect(Collectors.toList());
                    items.forEach(orderItem::addItem);
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    private Item convertItem(ItemDto itemDto) {
        if (ItemType.FOOD.equals(itemDto.getType())) {
            Food food = new Food();
            food.setPrice(itemDto.getPrice());
            food.setStockQuantity(itemDto.getStockQuantity());
            food.setChef(itemDto.getChef());
            return food;
        }

        if (ItemType.FURNITURE.equals(itemDto.getType())) {
            Furniture furniture = new Furniture();
            furniture.setPrice(itemDto.getPrice());
            furniture.setStockQuantity(itemDto.getStockQuantity());
            furniture.setHeight(itemDto.getHeight());
            furniture.setWidth(itemDto.getWidth());
            return furniture;
        }

        if (ItemType.CAR.equals(itemDto.getType())) {
            Car car = new Car();
            car.setPrice(itemDto.getPrice());
            car.setStockQuantity(itemDto.getStockQuantity());
            car.setPower(itemDto.getPower());
            return car;
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");

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
