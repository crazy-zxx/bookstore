package com.me.bean;

import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
public class ShoppingCart {

    // 1 -> *
    private Map<Integer, ShoppingCartItem> books = new HashMap<>();

    /**
     * 修改指定购物项的数量
     */
    public void updateItemQuantity(Integer bookId, int quantity){
        ShoppingCartItem sci =books.get(bookId);
        if(sci != null){
            sci.setQuantity(quantity);
        }
    }

    /**
     * 移除指定的购物项
     * @param bookId
     */
    public void removeItem(Integer bookId){
        books.remove(bookId);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        books.clear();
    }

    /**
     * 返回购物车是否为空
     * @return
     */
    public boolean isEmpty(){
        return books.isEmpty();
    }

    /**
     * 获取购物车中所有的商品的总的钱数
     * @return
     */
    public float getTotalMoney(){
        float total = 0;

        for(ShoppingCartItem sci: getItems()){
            total += sci.getItemMoney();
        }

        return total;
    }

    /**
     * 获取购物车中的所有的 ShoppingCartItem 组成的集合
     * @return
     */
    public Collection<ShoppingCartItem> getItems(){
        return books.values();
    }

    /**
     * 返回购物车中商品的总数量
     * @return
     */
    public int getBookNumber(){
        int total = 0;

        for(ShoppingCartItem sci: books.values()){
            total += sci.getQuantity();
        }

        return total;
    }

    public Map<Integer, ShoppingCartItem> getBooks() {
        return books;
    }

    /**
     * 检验购物车中是否有 id 指定的商品
     * @param bookId
     * @return
     */
    public boolean hasBook(Integer bookId){
        return books.containsKey(bookId);
    }

    /**
     * 向购物车中添加一件商品
     * @param book
     */
    public void addBook(Book book){
        //1. 检查购物车中有没有该商品, 若有, 则使其数量 +1, 若没有,
        //新创建其对应的 ShoppingCartItem, 并把其加入到 books 中
        ShoppingCartItem sci = books.get(book.getBookId());

        if(sci == null){
            sci = new ShoppingCartItem(book);
            books.put(book.getBookId(), sci);
        }else{
            sci.increment();
        }
    }

}
