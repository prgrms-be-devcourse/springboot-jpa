package org.prgrms.studyjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
