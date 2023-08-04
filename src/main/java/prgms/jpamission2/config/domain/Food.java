package prgms.jpamission2.config.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;
}
