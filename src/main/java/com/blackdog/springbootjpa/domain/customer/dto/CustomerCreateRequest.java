package com.blackdog.springbootjpa.domain.customer.dto;

import jakarta.validation.constraints.*;

public record CustomerCreateRequest(
        @NotBlank(message = "이름은 반드시 주어져야 합니다.")
        @Pattern(regexp = "^([가-힣]+|[a-zA-Z]){2,50}$", message = "부적절한 이름입니다.")
        String name,

        @Min(value = 1, message = "나이가 너무 어립니다.")
        @Max(value = 250, message = "나이가 너무 많습니다.")
        int age,

        @NotBlank(message = "이메일은 공백을 포함할 수 없습니다.")
        @Email(message = "이메일 형식이 맞지 않습니다.")
        @Size(max = 50, message = "이메일이 너무 깁니다.")
        String email
) {
}
