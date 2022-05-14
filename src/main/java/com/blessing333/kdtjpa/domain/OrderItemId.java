package com.blessing333.kdtjpa.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderItemId implements Serializable {
    private Long orderId;
    private Long itemId;
}
