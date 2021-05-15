package com.me.service;

import com.me.bean.Account;
import com.me.dao.AccountDAO;
import com.me.dao.impl.AccountDAOIml;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOIml();
	
	public Account getAccount(int accountId){
		return accountDAO.get(accountId);
	}
	
}
