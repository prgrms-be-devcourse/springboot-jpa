package com.prgms.springbootjpa.domain.order.item;

import com.prgms.springbootjpa.domain.order.BaseEntity;
import com.prgms.springbootjpa.domain.order.OrderItem;
import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Price price;
    
    @Embedded
    private Quantity stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();
}
