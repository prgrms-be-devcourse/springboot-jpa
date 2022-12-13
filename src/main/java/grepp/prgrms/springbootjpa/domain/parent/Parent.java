package grepp.prgrms.springbootjpa.domain.parent;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent {
    @EmbeddedId
    private ParentId id;
}
