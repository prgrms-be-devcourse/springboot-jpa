package com.example.weeklyjpa.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Keyboard")
@Getter
@NoArgsConstructor
public class Keyboard extends Item{
    private String maker;

    private Keyboard(String name,int price, int stockQuantity,String maker){
        super.setName(name);
        super.setPrice(price);
        super.setStockQuantity(stockQuantity);
        this.maker = maker;
    }

    public static Keyboard createKeyboard(String name, int price, int stockQuantity, String maker){
        return new Keyboard(name, price, stockQuantity, maker);
    }

}
