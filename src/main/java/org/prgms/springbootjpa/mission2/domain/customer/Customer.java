package org.prgms.springbootjpa.mission2.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    Long id;
    String firstName;
    String lastName;

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
}
