package com.part4.jpa2.domain.parent.idclass;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/*
 * IdClass를 사용하는 방식은 객체지향 스럽지 않아서 비추 
 * -> EmbeddedId - Embeddable 사용 권장
 */
@Deprecated
@Entity
@Getter
@Setter
@IdClass(ParentIdv1.class)
public class Parentv1 {
    @Id
    private String id1;
    @Id
    private String id2;
}
