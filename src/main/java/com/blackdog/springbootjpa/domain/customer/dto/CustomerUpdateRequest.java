package com.blackdog.springbootjpa.domain.customer.dto;

import jakarta.validation.constraints.*;

public record CustomerUpdateRequest(
        @NotBlank(message = "이름은 공백을 포함할 수 없습니다")
        @Pattern(regexp = "^([가-힣]+|[a-zA-Z]){2,50}$", message = "이름은 50자를 넘을 수 없습니다. ")
        String name,

        @Min(value = 1, message = "나이는 1이상 이어야 합니다.")
        @Max(value = 250, message = "나이는 250세 이하여야 합니다.")
        int age,

        @NotBlank(message = "이메일은 공백을 포함할 수 없습니다.")
        @Email(message = "이메일 형식이 맞지 않습니다.")
        @Size(max = 50, message = "이메일이 50자 이상입니다.")
        String email
) {
}
