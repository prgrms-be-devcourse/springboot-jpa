package com.example.springbootjpa.domain;

import com.example.springbootjpa.golbal.exception.DomainException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_USERNAME;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String address;

    public void updateCustomer(Customer customer) {
        this.username = customer.getUsername();
        this.address = customer.getAddress();
    }

    @Builder
    public Customer(Long id, String username, String address) {
        this.id = id;
        validateInformation(username, address);
        this.username = username;
        this.address = address;
    }

    private void validateInformation(String username, String address) {
        if (!StringUtils.hasText(username)) {
            throw new DomainException(INVALID_USERNAME);
        }

        if (!StringUtils.hasText(address)) {
            throw new DomainException(INVALID_USERNAME);
        }
    }
}