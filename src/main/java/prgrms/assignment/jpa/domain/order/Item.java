package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private int price;

    @Column(name = "stock_quantity")
    private long stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Item(int price, long stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
