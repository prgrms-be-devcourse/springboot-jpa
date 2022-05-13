package com.pppp0722.springbootjpa.domain.parent;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent {

    @EmbeddedId
    private ParentId id;
}
