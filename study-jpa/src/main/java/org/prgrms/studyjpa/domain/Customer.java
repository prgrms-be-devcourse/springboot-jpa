package org.prgrms.studyjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
