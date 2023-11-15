package com.sehee.weeklyjpa.domain.parent;

import com.sehee.weeklyjpa.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ParentId.class)
public class Parent extends BaseEntity {
    @Id
    private String id1;
    @Id
    private String id2;
}
