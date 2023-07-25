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

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_ADDRESS;
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

    @Builder
    public Customer(Long id, String username, String address) {
        this.id = id;
        validateUsername(username);
        this.username = username;
        validateUsername(address);
        this.address = address;
    }

    public void updateUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public void updateAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new DomainException(INVALID_USERNAME);
        }
    }

    private void validateAddress(String address) {
        if (!StringUtils.hasText(address)) {
            throw new DomainException(INVALID_ADDRESS);
        }
    }
}