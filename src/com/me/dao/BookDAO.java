package com.me.dao;

import com.me.bean.Book;
import com.me.bean.ShoppingCartItem;
import com.me.web.CriteriaBook;
import com.me.web.Page;

import java.util.Collection;
import java.util.List;

public interface BookDAO {

    /**
     *
     * @param bookId
     * @return
     */
    public abstract Book getBook(int bookId);

    /**
     *
     * @param cb
     * @return
     */
    public abstract Page<Book> getPage(CriteriaBook cb);

    /**
     *
     * @param cb
     * @return
     */
    public abstract long getTotalBookNumber(CriteriaBook cb);

    /**
     *
     * @param cb
     * @param pageSize
     * @return
     */
    public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);

    /**
     *
     * @param bookId
     * @return
     */
    public abstract int getStoreNumber(Integer bookId);

    /**
     *
     * @param items
     */
    public abstract void batchUpdateStoreNumberAndSalesAmount(
            Collection<ShoppingCartItem> items);


}
