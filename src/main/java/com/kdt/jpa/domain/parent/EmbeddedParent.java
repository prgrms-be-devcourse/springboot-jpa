package com.kdt.jpa.domain.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class EmbeddedParent {
    @EmbeddedId
    private EmbeddedParentId id;
}
