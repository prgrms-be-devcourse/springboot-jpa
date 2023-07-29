package com.programmers.springbootjpa.dto.request;

import com.programmers.springbootjpa.domain.customer.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerUpdateRequest {

    @PositiveOrZero(message = "id값은 0이상 이어야합니다.")
    private Long id;
    @NotBlank(message = "닉네임은 공백이거나 값이 없으면 안됩니다.")
    private String nickName;
    @NotNull(message = "주소값은 없을 수 없습니다.")
    private Address address;

}
