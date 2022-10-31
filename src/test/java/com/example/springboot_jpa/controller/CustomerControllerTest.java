package com.example.springboot_jpa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot_jpa.entity.Customer;
import com.example.springboot_jpa.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

  @Test
  void should_delete_customer_using_posted_json() throws Exception {

    String json = "{\"id\":1}";
    // Given
    var idArgument = ArgumentCaptor.forClass(Long.class);

    // When
    mockMvc.perform(
            delete("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().is2xxSuccessful());

    // Then
    verify(repository, times(1)).deleteById(idArgument.capture());
    var usedCustomerId = idArgument.getValue();
    assertThat(usedCustomerId).isEqualTo(1);
  }

  @Test
  void should_update_customer_using_posted_json() throws Exception {
    // Given
    var updatedCustomer = new Customer(1, "Sam", "Jones");
    var updatedJson = objectMapper.writeValueAsString(updatedCustomer);
    // When
    mockMvc.perform(
            put("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(updatedJson))
        .andExpect(status().is2xxSuccessful()).andExpect(content().json(updatedJson));

    // then
    var customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(repository, times(1)).save(customerArgumentCaptor.capture());
    assertThat(customerArgumentCaptor.getValue()).isEqualTo(updatedCustomer);

  }

  @Test
  void should_return_all_customer() throws Exception {

    // Given
    var customerList = List.of(new Customer(1, "미성", "균"), new Customer(2, "Steven", "Strange"));
    var expectedResponseJson = objectMapper.writeValueAsString(customerList);

    // When
    when(repository.findAll()).thenReturn(customerList);

    mockMvc.perform(get("/api/v1/customers"))
        // Then
        .andExpectAll(status().is2xxSuccessful(), content().json(expectedResponseJson));

  }

}
