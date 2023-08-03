package com.jpaweekily.domain.order.infrastructrue;

import com.jpaweekily.domain.order.OrderProduct;

import java.util.List;

public interface CustomOrderProductRepository {
    void saveAll(List<OrderProduct> orderProducts);
}
