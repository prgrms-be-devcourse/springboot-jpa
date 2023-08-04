package prgms.jpamission2.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }
}
