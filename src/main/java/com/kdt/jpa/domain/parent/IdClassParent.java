package com.kdt.jpa.domain.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(IdClassParentId.class)
public class IdClassParent {
    @Id
    private String id1;
    @Id
    private String id2;
}
