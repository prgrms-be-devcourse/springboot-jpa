package org.programmers.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@IdClass(ParentId.class)
public class Parent {

    @Id
    private String id1;
    @Id
    private String id2;
}
