package org.programmers.jpaweeklymission.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.jpaweeklymission.global.BaseEntity;

@Table(name = "customers")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20)
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 20)
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Builder
    private Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeEntity(Customer customer) {
        changeFirstName(customer.firstName);
        changeLastName(customer.lastName);
    }

    private void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void changeLastName(String lastName) {
        this.lastName = lastName;
    }
}
