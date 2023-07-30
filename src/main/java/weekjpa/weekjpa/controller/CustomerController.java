package weekjpa.weekjpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weekjpa.weekjpa.dto.CustomerCreateRequest;
import weekjpa.weekjpa.dto.CustomerResponse;
import weekjpa.weekjpa.dto.CustomerUpdateRequest;
import weekjpa.weekjpa.dto.CustomerUpdateResponse;
import weekjpa.weekjpa.service.CustomerService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customers")
    @ResponseStatus(CREATED)
    public Long create(@RequestBody CustomerCreateRequest createRequest) {
        return customerService.create(createRequest);
    }

    @GetMapping("/customers/{id}")
    @ResponseStatus(OK)
    public CustomerResponse findCustomer(@PathVariable Long id) {
        return customerService.find(id);
    }

    @PatchMapping("/customers/{id}")
    @ResponseStatus(OK)
    public CustomerUpdateResponse updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
