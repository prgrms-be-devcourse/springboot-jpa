package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidItemException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {

    @Test
    void 재고수량_보다_많은_수를_감소시키면_예외가_발생한다() {
        // given
        Item item = new Item("아이폰24", 10);

        // when & then
        assertThatThrownBy(() -> item.decreaseStock(15))
                .isInstanceOf(InValidItemException.class)
                .hasMessage("재고수량은 0이상이여야 합니다.");
    }
}
