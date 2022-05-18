package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    @NotBlank
    private String address;
    @NotBlank
    private String postcode;
    @NotBlank
    private String orderStatus;
    private LocalDateTime orderDatetime;
    @NotNull
    private @Valid CustomerDto customer;
    @NotEmpty
    private List<@Valid OrderItemDto> orderItems;
}
