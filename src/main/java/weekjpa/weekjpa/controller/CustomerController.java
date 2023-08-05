package weekjpa.weekjpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weekjpa.weekjpa.dto.CustomerCreateRequest;
import weekjpa.weekjpa.dto.CustomerResponse;
import weekjpa.weekjpa.dto.CustomerUpdateRequest;
import weekjpa.weekjpa.service.CustomerService;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Long create(@RequestBody CustomerCreateRequest createRequest) {
        return customerService.create(createRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CustomerResponse findCustomer(@PathVariable Long id) {
        return customerService.find(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        customerService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
