package com.aj.utility;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ActionFilter implements Filter {
    public ActionFilter() {
        // TODO Auto-generated constructor stub
    }

	FilterConfig config;
	public void init(FilterConfig fConfig) throws ServletException {
		config = fConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletResponse res = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			HttpSession sess = req.getSession(false);
			String path = req.getServletPath();
			
				if (sess != null) {
					if (sess.getAttribute("isLogin") != null && (((String)sess.getAttribute("isLogin")).equals("yes"))) {
						res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1. 
						res.setHeader("Pragma", "no-cache"); // HTTP 1.0. 
						res.setDateHeader("Expires", 0);	
						chain.doFilter(request, response);
					}else{
						config.getServletContext()
						.getRequestDispatcher("/index.jsp")
						.forward(request, response);
				return;
					}
				}else{
					config.getServletContext()
					.getRequestDispatcher("/index.jsp")
					.forward(request, response);
			return;
				}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void destroy() {
	}

}
