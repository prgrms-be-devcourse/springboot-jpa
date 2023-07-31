package com.example.springbootjpa.domain.customer;

import com.example.springbootjpa.golbal.RegexPattern;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_USERNAME_OR_ADDRESS;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String address;

    @Builder
    public Customer(Long id, String username, String address) {
        this.id = id;
        this.username = validateString(username);
        this.address = validateString(address);
    }

    public void updateUsername(String username) {
        validateString(username);
        this.username = username;
    }

    public void updateAddress(String address) {
        validateString(address);
        this.address = address;
    }

    private String validateString(String value) {
        if (!StringUtils.hasText(value)
                || !RegexPattern.STRING_REGEX_PATTERN.matcher(value).matches()) {
            throw new InvalidDomainConditionException(INVALID_USERNAME_OR_ADDRESS);
        }
        return value;
    }
}