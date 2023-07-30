package org.wonu606.jpashop.customer.service.dto;

import java.util.List;

public record CustomerResults(List<CustomerResult> results) {

    public CustomerResults(List<CustomerResult> results) {
        this.results = List.copyOf(results);
    }
}
