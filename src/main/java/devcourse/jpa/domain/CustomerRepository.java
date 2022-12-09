package devcourse.jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.firstName=:firstName where c.id=:id")
    void updateCustomerFirstName(
            @Param(value = "id") Long id,
            @Param(value = "firstName") String firstName
    );
}
