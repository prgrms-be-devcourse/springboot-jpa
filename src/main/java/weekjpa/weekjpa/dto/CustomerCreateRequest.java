package weekjpa.weekjpa.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerCreateRequest(
        @NotBlank(message = "이름을 입력해 주세요.") String firstName,
        @NotBlank(message = "성을 입력해 주세요.") String lastName) {
}
