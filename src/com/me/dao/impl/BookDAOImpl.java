package com.me.dao.impl;

import com.me.bean.Book;
import com.me.bean.ShoppingCartItem;
import com.me.dao.BookDAO;
import com.me.web.CriteriaBook;
import com.me.web.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

    @Override
    public Book getBook(int id) {
        String sql = "SELECT bookid, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE bookid = ?";
        return query(sql, id);
    }

    //3.
    @Override
    public Page<Book> getPage(CriteriaBook cb) {
        Page<Book> page = new Page<>(cb.getPageNo());

        page.setTotalItemNumber(getTotalBookNumber(cb));
        cb.setPageNo(page.getPageNo());
        page.setList(getPageList(cb, 3));

        return page;
    }

    //1.
    @Override
    public long getTotalBookNumber(CriteriaBook cb) {
        String sql = "SELECT count(*) FROM mybooks WHERE price >= ? AND price <= ?";
        return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
    }

    //2.

    /**
     * MySQL ��ҳʹ�� LIMIT, ���� fromIndex �� 0 ��ʼ��
     */
    @Override
    public List<Book> getPageList(CriteriaBook cb, int pageSize) {
        String sql = "SELECT bookid, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks " +
                "WHERE price >= ? AND price <= ? " +
                "LIMIT ?, ?";

        return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(),
                (cb.getPageNo() - 1) * pageSize, pageSize);
    }

    @Override
    public int getStoreNumber(Integer id) {
        String sql = "SELECT storeNumber FROM mybooks WHERE bookid = ?";
        return getSingleVal(sql, id);
    }

    @Override
    public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
        String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, " +
                "storeNumber = storeNumber - ? " +
                "WHERE bookid = ?";
        Object[][] params = null;
        params = new Object[items.size()][3];
        List<ShoppingCartItem> scis = new ArrayList<>(items);
        for (int i = 0; i < items.size(); i++) {
            params[i][0] = scis.get(i).getQuantity();
            params[i][1] = scis.get(i).getQuantity();
            params[i][2] = scis.get(i).getBook().getBookId();
        }
        batch(sql, params);
    }

}
