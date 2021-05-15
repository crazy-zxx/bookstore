package com.me.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeItem {

    private Integer tradeItemId;
    private Integer bookId;
    private int quantity;
    private Integer tradeId;

    // 1 -> 1
    private Book book;

    public TradeItem(Integer tradeItemId, Integer bookId, int quantity, Integer tradeId) {
        super();
        this.tradeItemId = tradeItemId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.tradeId = tradeId;
    }

}
