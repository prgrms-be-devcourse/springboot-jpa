package com.devcourse.springbootjpa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devcourse.springbootjpa.domain.order.Item;
import com.devcourse.springbootjpa.exception.InvalidQuantityException;

class ItemTest {
	@Test
	@DisplayName("재고보다 더 많은 수량을 주문하면 InvalidQuantityException이 발생한다")
	void addItemTest() {
		// given
		Item item = new Item(10000, 10);
		// when
		// then
		assertThrows(
				InvalidQuantityException.class,
				() -> item.reduceQuantity(11)
		);

	}
}
