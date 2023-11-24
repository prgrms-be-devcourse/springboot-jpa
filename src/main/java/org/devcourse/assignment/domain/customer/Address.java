package org.devcourse.assignment.domain.customer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Embeddable
public record Address (
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String zipCode
) {
    protected Address() {
        this(null, null, null);
    }

    @Builder
    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
