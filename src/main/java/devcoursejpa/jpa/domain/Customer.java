package devcoursejpa.jpa.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    private Long id;
    @Embedded
    private Name name;

    protected Customer() {
    }

    public Customer(Long id, Name name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
    public void changeName(Name name) {
        this.name = name;
    }
}