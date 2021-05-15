package com.me.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.me.bean.*;
import com.me.dao.*;
import com.me.dao.impl.*;
import com.me.web.CriteriaBook;
import com.me.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}

	public Book getBook(int bookId) {
		return bookDAO.getBook(bookId);
	}

	public boolean addToCart(int bookId, ShoppingCart sc) {
		Book book = bookDAO.getBook(bookId);
		
		if(book != null){
			sc.addBook(book);
			return true;
		}
		
		return false;
	}

	public void removeItemFromShoppingCart(ShoppingCart sc, int bookId) {
		sc.removeItem(bookId);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int bookId, int quantity) {
		sc.updateItemQuantity(bookId, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOIml();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {

		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());

		Trade trade = new Trade();
		//时间转换
		trade.setTradeTime(new Timestamp(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);

		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getBookId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);

		shoppingCart.clear();
	}
	
}
