package com.example.springbootpart4.domain.parent;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@IdClass(ParentId.class)
public class Parent {
//    @Id
//    private String id1;
//    @Id
//    private String id2;

    @EmbeddedId
    private ParentId id;
}
