package com.me.service;

import com.me.bean.Trade;
import com.me.bean.TradeItem;
import com.me.bean.User;
import com.me.dao.BookDAO;
import com.me.dao.TradeDAO;
import com.me.dao.TradeItemDAO;
import com.me.dao.UserDAO;
import com.me.dao.impl.BookDAOImpl;
import com.me.dao.impl.TradeDAOImpl;
import com.me.dao.impl.TradeItemDAOImpl;
import com.me.dao.impl.UserDAOImpl;

import java.util.Iterator;
import java.util.Set;

public class UserService {

	private UserDAO userDAO = new UserDAOImpl();
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
	
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	
	public User getUserWithTrades(String username){

		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}

		int userId = user.getUserId();

		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if(trades != null){
			Iterator<Trade> tradeIt = trades.iterator();
			
			while(tradeIt.hasNext()){
				Trade trade = tradeIt.next();
				
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items != null){
					for(TradeItem item: items){
						item.setBook(bookDAO.getBook(item.getBookId())); 
					}
					
					if(items != null && items.size() != 0){
						trade.setItems(items);						
					}
				}
				
				if(items == null || items.size() == 0){
					tradeIt.remove();	
				}
				
			}
		}
		
		if(trades != null && trades.size() != 0){
			user.setTrades(trades);			
		}
		
		return user;
	}
	
}
