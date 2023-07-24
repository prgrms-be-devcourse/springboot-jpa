package com.programmers.week.customer.ui;

import com.programmers.week.customer.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/customers")
  @ResponseStatus(HttpStatus.CREATED)
  public Long create(CustomerCreateRequest createRequest) {
    return customerService.create(createRequest);
  }

}
