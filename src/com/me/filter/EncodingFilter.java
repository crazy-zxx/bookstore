package com.me.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@Slf4j
@WebFilter(filterName = "encodingFilter",value = "/*")
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);

		chain.doFilter(request, response);
	}

	private String encoding = null;

	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getServletContext().getInitParameter("encoding");

		if (encoding == null){
			log.error("web.xml未正确配置用于指定页面编码的全局参数");
		}
	}

}
