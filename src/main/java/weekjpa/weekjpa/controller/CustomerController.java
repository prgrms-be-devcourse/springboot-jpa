package weekjpa.weekjpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import weekjpa.weekjpa.dto.CustomerCreateRequest;
import weekjpa.weekjpa.dto.CustomerResponse;
import weekjpa.weekjpa.dto.CustomerUpdateRequest;
import weekjpa.weekjpa.service.CustomerService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Long create(@RequestBody @Valid CustomerCreateRequest createRequest) {
        return customerService.create(createRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CustomerResponse findCustomer(@PathVariable Long id) {
        return customerService.find(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,
                                               @RequestBody @Valid CustomerUpdateRequest request) {
        customerService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
