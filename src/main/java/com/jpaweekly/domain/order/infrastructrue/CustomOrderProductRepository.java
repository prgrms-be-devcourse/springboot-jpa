package com.jpaweekly.domain.order.infrastructrue;

import com.jpaweekly.domain.order.OrderProduct;

import java.util.List;

public interface CustomOrderProductRepository {
    void saveAll(List<OrderProduct> orderProducts);
}
