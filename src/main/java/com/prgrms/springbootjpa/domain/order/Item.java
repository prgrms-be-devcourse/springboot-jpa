package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.prgrms.springbootjpa.global.util.Validator.*;

@Entity
@Table(name = "item")
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    List<OrderItem> orderItems = new ArrayList<>();

    protected Item() {
    }

    public Item(String name, int price, int stockQuantity) {
        validateItemField(name, price, stockQuantity);
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }

    private void validateItemField(String name, int price, int stockQuantity) {
        if(!StringUtils.hasText(name)) {
            throw new WrongFieldException("Item.name", name, "please input name");
        }

        if(!validateFieldLength(name, 1, 30)) {
            throw new WrongFieldException("Item.name", name, "1 <= name <= 30");
        }

        if(!validateNumberSize(price, 100)) {
            throw new WrongFieldException("Item.price", price, "100 <= price");
        }

        if(!validateNumberSize(stockQuantity, 0)) {
            throw new WrongFieldException("Item.stockQuantity", stockQuantity, "0 <= stockQuantity");
        }
    }
}
