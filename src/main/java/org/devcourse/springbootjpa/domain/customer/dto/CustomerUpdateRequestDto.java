package org.devcourse.springbootjpa.domain.customer.dto;

public record CustomerUpdateRequestDto(Long id,
                                       String firstName,
                                       String lastName) {
}
