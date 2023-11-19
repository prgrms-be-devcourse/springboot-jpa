package com.example.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public List<CustomerResponseDto> getCustomerList(){
        return customerService.getCustomers();
    }

    @PostMapping()
    public Long saveNewCustomer(@RequestBody CustomerSaveRequestDto requestDto){
        return customerService.saveCustomer(requestDto);
    }

    @PatchMapping("/{customerId}")
    public Long updateCustomerInfo(@RequestBody CustomerUpdateRequestDto requestDto, @PathVariable Long customerId){
        return customerService.updateCustomer(requestDto, customerId);
    }

    @DeleteMapping("/{customerId}")
    public Long deleteCustomer(@PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
    }
}
