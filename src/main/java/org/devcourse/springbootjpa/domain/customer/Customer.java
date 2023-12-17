package org.devcourse.springbootjpa.domain.customer;

import lombok.*;
import org.devcourse.springbootjpa.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Builder
    private Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Customer createWithNames(String firstName, String lastName) {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    public static Customer create(Long id, String firstName, String lastName) {
        return Customer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
