package com.example.springbootjpa.domain.customer;

import com.example.springbootjpa.domain.order.Order;
import com.example.springbootjpa.golbal.ErrorCode;
import com.example.springbootjpa.golbal.exception.InvalidDomainConditionException;
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
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static com.example.springbootjpa.golbal.ErrorCode.INVALID_USERNAME;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(Long id, String username, String address) {
        this.id = id;
        validateUsername(username);
        this.username = username;
        validateAddress(address);
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
            throw new InvalidDomainConditionException(INVALID_USERNAME);
        }
    }

    private void validateAddress(String address) {
        if (!StringUtils.hasText(address)) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_ADDRESS);
        }
    }
}