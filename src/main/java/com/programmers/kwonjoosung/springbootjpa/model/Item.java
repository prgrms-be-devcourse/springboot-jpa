package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.UUID;


@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "items")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", length = 20, nullable = false)
    @Size(max = 20, message = "제품의 이름은 20자 이내로 입력해주세요.")
    protected String name;

    @Column(name = "price", nullable = false)
    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    protected int price;

    public void changePrice(int price) {
        this.price = price;
    }

    public void applyDiscountByAmount(int amount) {
        this.price -= amount;
    }

    public void applyDiscountByPercent(long percent) {
        this.price -= (this.price * percent) / 100;
    }

}
