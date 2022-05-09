package com.example.springboot_jpa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot_jpa.entity.Customer;
import com.example.springboot_jpa.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CustomerRepository repository;

  @Test
  void should_create_customer_by_posted_json() throws Exception {
    var customer = new Customer(1, "Minsung", "Kim");
    String json = objectMapper.writeValueAsString(customer);
    // Given
    var customerArgument = ArgumentCaptor.forClass(Customer.class);

    // When
    mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().is2xxSuccessful());

    // Then
    verify(repository, times(1)).save(customerArgument.capture());
    var usedCustomer = customerArgument.getValue();
    assertThat(usedCustomer).isEqualTo(customer);
  }

}
