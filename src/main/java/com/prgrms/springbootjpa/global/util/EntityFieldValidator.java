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

    private static boolean validateFieldLength(String field, int min, int max) {
        if(min <= field.length() && field.length() <= max) {
            return true;
        } else {
            return false;
        }
    }
}
