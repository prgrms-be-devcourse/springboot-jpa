package com.blackdog.springbootjpa.domain.item.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
@Embeddable
public class OriginNation {
    @Column(name = "nation", length = 50, nullable = false)
    private String nation;
}
