package kr.co.springbootjpaweeklymission.member.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Column(name = "street", length = 30, nullable = false)
    private String street;

    @Column(name = "detail", length = 30)
    private String detail;

    @Builder
    private Address(String street, String detail) {
        this.street = street;
        this.detail = detail;
    }
}
