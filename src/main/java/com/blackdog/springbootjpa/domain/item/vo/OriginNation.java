package com.blackdog.springbootjpa.domain.item.vo;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OriginNation {
    @NotBlank
    private String nation;
}
