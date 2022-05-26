package com.prgrms.springbootjpa.global.util;

import com.prgrms.springbootjpa.domain.order.Order;
import com.prgrms.springbootjpa.domain.order.OrderStatus;
import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntityFieldValidator {
    public static void validateCustomerField(String firstName, String lastName) {
        if(!StringUtils.hasText(firstName)) {
            throw new WrongFiledException("Customer.firstName", firstName, "please input firstName");
        }

        if(!validateFieldLength(firstName, 1, 30)) {
            throw new WrongFiledException("Customer.firstName", firstName, "1 <= firstName <= 30");
        }

        if(!StringUtils.hasText(lastName)) {
            throw new WrongFiledException("Customer.lastName", lastName, "please input lastName");
        }

        if(!validateFieldLength(lastName, 1, 30)) {
            throw new WrongFiledException("Customer.lastName", lastName, "1 <= lastName <= 30");
        }
    }

    public static void validateItemField(String name, int price, int stockQuantity) {
        if(!StringUtils.hasText(name)) {
            throw new WrongFiledException("Item.name", name, "please input name");
        }

        if(!validateFieldLength(name, 1, 30)) {
            throw new WrongFiledException("Item.name", name, "1 <= name <= 30");
        }

        if(!validateNumberSize(price, 100)) {
            throw new WrongFiledException("Item.price", price, "100 <= price");
        }

        if(!validateNumberSize(stockQuantity, 0)) {
            throw new WrongFiledException("Item.stockQuantity", stockQuantity, "0 <= stockQuantity");
        }
    }

    public static void validateOrderField(String uuid, OrderStatus orderStatus, LocalDateTime createdAt) {
        if(uuid == null) {
            throw new WrongFiledException("Order.uuid", uuid, "uuid is required field");
        }

        if(orderStatus == null) {
            throw new WrongFiledException("Order.orderStatus", orderStatus, "orderStatus is required field");
        }

        if(createdAt == null) {
            throw new WrongFiledException("Order.createdAt", orderStatus, "createdAt is required field");
        }
    }

    public static void validateMemberField(String name, String nickName, int age, String address) {
        if(!StringUtils.hasText(name)) {
            throw new WrongFiledException("Member.name", name, "please input name");
        }

        if(!validateFieldLength(name, 1, 30)) {
            throw new WrongFiledException("Member.name", name, "1 <= name <= 30");
        }

        if(!StringUtils.hasText(nickName)) {
            throw new WrongFiledException("Member.nickName", nickName, "please input nickName");
        }

        if(!validateFieldLength(nickName, 1, 30)) {
            throw new WrongFiledException("Member.nickName", nickName, "1 <= nickName <= 30");
        }

        if(!validateNumberSize(age, 1)) {
            throw new WrongFiledException("Member.age", 1, "1 <= age");
        }

        if(!StringUtils.hasText(address)) {
            throw new WrongFiledException("Member.address", address, "please input address");
        }

        if(!validateFieldLength(address, 1, 200)) {
            throw new WrongFiledException("Member.address", address, "1 <= address <= 30");
        }
    }

    public static void validateOrderItem(int price, OrderStatus orderStatus) {
        if(!validateNumberSize(price, 100)) {
            throw new WrongFiledException("OrderItem.price", price, "100 <= price");
        }

        if(orderStatus == null) {
            throw new WrongFiledException("OrderItem.orderStatus", orderStatus, "orderStatus is required field");
        }
    }

    private static boolean validateFieldLength(String field, int min, int max) {
        if(min <= field.length() && field.length() <= max) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean validateNumberSize(int number, int min) {
        if(min > number) {
            return false;
        }else{
            return true;
        }
    }
}
