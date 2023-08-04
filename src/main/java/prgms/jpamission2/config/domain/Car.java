package prgms.jpamission2.config.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {
    private int power;
}
