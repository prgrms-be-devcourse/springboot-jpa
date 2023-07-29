package com.programmers.springbootjpa.dto.request;

import com.programmers.springbootjpa.domain.customer.Address;
import com.programmers.springbootjpa.domain.customer.Customer;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerCreateRequest {

    @NotBlank(message = "이름은 공백이거나 값이 없으면 안됩니다.")
    private String name;
    @Min(value = 1, message = "나이는 한살보다 많아야합니다.")
    @Max(value = 100, message = "나이는 백살보다 적여야합니다.")
    @NotNull(message = "나이는 값이 없으면 안됩니다.")
    private Integer age;
    @NotBlank(message = "닉네임은 공백이거나 값이 없으면 안됩니다.")
    private String nickName;
    @NotNull(message = "주소값은 없을 수 없습니다.")
    private Address address;

    public Customer of() {
        return Customer.builder()
                .name(name)
                .age(age)
                .nickName(nickName)
                .address(address)
                .build();
    }

}
