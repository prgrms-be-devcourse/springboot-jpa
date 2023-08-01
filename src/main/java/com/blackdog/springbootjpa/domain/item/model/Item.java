package com.blackdog.springbootjpa.domain.item.model;

import com.blackdog.springbootjpa.domain.item.vo.OriginNation;
import com.blackdog.springbootjpa.domain.item.vo.Price;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Table(name = "items")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @Embedded
    @Column(nullable = false)
    private OriginNation nation;

    @Builder
    protected Item(
            Price price,
            OriginNation nation
    ) {
        validate(price, nation);
        this.price = price;
        this.nation = nation;
    }

    private void validate(Price price, OriginNation nation) {
        Assert.notNull(price, "price 가 존재하지 않습니다.");
        Assert.notNull(nation, "nation 가 존재하지 않습니다.");
    }
}
