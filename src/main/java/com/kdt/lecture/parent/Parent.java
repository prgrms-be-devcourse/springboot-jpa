package com.kdt.lecture.parent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(value = ParentId.class)
public class Parent {
    @Id
    private String id1;
    @Id
    private String id2;
}
