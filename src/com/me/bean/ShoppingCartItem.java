package com.me.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingCartItem {

    private Book book;
    private int quantity;

    public ShoppingCartItem(Book book) {
        this.book = book;
        this.quantity = 1;
    }

    /**
     * 返回该商品在购物车中的钱数
     * @return
     */
    public float getItemMoney(){
        return book.getPrice() * quantity;
    }

    /**
     * 使商品数量 + 1
     */
    public void increment(){
        quantity++;
    }
}
