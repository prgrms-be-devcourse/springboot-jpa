package com.sehee.weeklyjpa.domain.parent;

import com.sehee.weeklyjpa.domain.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent2 extends BaseEntity {
    @EmbeddedId
    private ParentId id;
}
