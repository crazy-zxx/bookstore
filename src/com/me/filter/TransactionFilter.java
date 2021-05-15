package com.me.filter;

import com.me.utils.JDBCUtils;
import com.me.web.ConnectionContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Servlet Filter implementation class TranactionFilter
 */
@Slf4j
@WebFilter(filterName = "transactionFilter",value = "/*")
public class TransactionFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			connection.setAutoCommit(false);
			//绑定数据库连接到当前线程
			ConnectionContext.getInstance().bind(connection);

			chain.doFilter(request, response);

			connection.commit();
		} catch (Exception e) {
			log.warn(e.getMessage());

			try {
				connection.rollback();
			} catch (SQLException ee) {
				log.warn(ee.getMessage());
			}
			
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
			
		} finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(connection);
		}
		
	}

}
