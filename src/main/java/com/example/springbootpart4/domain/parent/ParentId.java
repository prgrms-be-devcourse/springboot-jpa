package com.example.springbootpart4.domain.parent;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // @EmbeddedId 전략을 이용한 식별자 클래스
public class ParentId implements Serializable {
    private String id1;
    private String id2;
}
