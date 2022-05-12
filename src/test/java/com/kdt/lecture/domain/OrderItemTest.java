package com.kdt.lecture.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kdt.lecture.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

  private Member createMember() {
    return new Member("name", "nickname", 20, "서울");
  }

  private Item createItem() {
    return new Item(1000, 10);
  }

  @Test
  @DisplayName("주문상품 생성 시 상품 재고보다 많이 생성하는 경우 예외가 발생한다.")
  void test_throw_error_order_item_over_stock_quantity() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();

    //when
    int overStockQuantity = 11;

    //then
    assertThatThrownBy(() -> new OrderItem(order, item, overStockQuantity))
        .isInstanceOf(SoldOutException.class);
  }

  @Test
  @DisplayName("주문상품 생성 시 요청 수만큼 상품 재고가 줄어든다.")
  void test_decrease_item_stock_quantity() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();
    int stockQuantity = item.getStockQuantity();

    //when
    int quantity = 2;
    OrderItem orderItem = new OrderItem(order, item, quantity);

    //then
    assertThat(item.getStockQuantity()).isEqualTo(stockQuantity - quantity);
  }
}