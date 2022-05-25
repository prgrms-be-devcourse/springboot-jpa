package com.prgrms.springbootjpa.global.util;

import com.prgrms.springbootjpa.global.exception.WrongFiledException;
import org.springframework.util.StringUtils;

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
