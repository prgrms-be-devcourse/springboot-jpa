package com.programmers.week.customer.ui;

import com.programmers.week.customer.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/customers")
  public Long create(@RequestBody CustomerCreateRequest createRequest) {
    return customerService.create(createRequest);
  }

  @ResponseStatus
  @GetMapping("/customers/{id}")
  public CustomerResponse findById(@PathVariable Long id) {
    CustomerResponse customer = customerService.findById(id);
    return customer;
  }

  @ResponseStatus
  @GetMapping
  public Page<CustomerResponse> findAll(@ModelAttribute PageRequestDto pageRequestDto) {
    PageRequest pageRequest = PageRequest.of(pageRequestDto.page(), pageRequestDto.size());
    return customerService.findAll(pageRequest);
  }

  @ResponseStatus
  @PatchMapping("/customers/update")
  public Long update(@RequestBody CustomerUpdateRequest customerUpdateRequest) {
    return customerService.update(customerUpdateRequest);
  }

  @ResponseStatus
  @DeleteMapping("/customers/{id}")
  public void delete(@PathVariable Long id) {
    customerService.deleteById(id);
  }

}
