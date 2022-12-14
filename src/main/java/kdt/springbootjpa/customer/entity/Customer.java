package kdt.springbootjpa.customer.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20, message = "20자 이내로 입력해주세요")
    private String firstName;

    @NotBlank
    @Size(max = 20, message = "20자 이내로 입력해주세요")
    private String lastName;

    @Builder
    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public void changeLastName(String newLastName) {
        this.lastName = newLastName;
    }
}
