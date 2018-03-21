package com.ktds.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginFailInterceptor extends HandlerInterceptorAdapter {

	private Map<String, Integer> failList;

	public LoginFailInterceptor() {
		this.failList = new HashMap<String, Integer>();
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {

		if (request.getMethod().equalsIgnoreCase("post")) {
			String userId = request.getParameter("email");
			String password = request.getParameter("password");

			if (userId.equals("admin")) {
				if (!password.equals("1234")) {
					if (!failList.containsKey(userId)) {
						this.failList.put(userId, 0);
					} else {
						int failCount = this.failList.get(userId);
						this.failList.put(userId, failCount + 1);
					}
				}
			} 
			
//			if(this.failList.containsKey(userId)) {
//				if (this.failList.get(userId) >= 3) {
//					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/error/loginFail.jsp");
//					rd.forward(request, response);
//
//					return false;
//				}
//			}			
		}

		return true;
	}
}
