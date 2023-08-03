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
import org.programmers.jpaweeklymission.order.domain.Order;
import java.util.ArrayList;
import java.util.List;

@Table(name = "customers")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 20)
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Size(min = 1, max = 20)
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

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

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }
}
