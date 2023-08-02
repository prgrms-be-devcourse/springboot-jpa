package com.example.springjpamission.customer.service.dto;

public record UpdateCustomerRequest(Long id,
                                    String firstName,
                                    String lastName) { }
