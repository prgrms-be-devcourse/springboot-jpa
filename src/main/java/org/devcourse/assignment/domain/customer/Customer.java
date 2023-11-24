package org.devcourse.assignment.domain.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    @NotBlank
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    @NotBlank
    private String nickName;

    @Column(name = "age")
    @Range(min = 0, max = 200)
    private Integer age;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "zip_code", column = @Column(name = "zip_code"))
    })
    private Address address;

    @Column(name = "description")
    private String description;

    @Builder
    public Customer(String name, String nickName, Integer age, Address address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void saveAddress(Address address) {
        this.address = address;
    }
}
