package devcoursejpa.jpa.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private Long id;
    @Embedded
    private Name name;

    public Customer() {
    }

    public Customer(Long id, Name name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void changeName(Name name) {
        this.name = name;
    }
}