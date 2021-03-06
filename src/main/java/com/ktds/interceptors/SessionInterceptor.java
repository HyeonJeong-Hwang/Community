package com.ktds.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.member.constants.Member;

public class SessionInterceptor extends HandlerInterceptorAdapter{
	
	private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object controller) throws Exception{
		
		String contextPath = request.getContextPath();
		
		if(request.getSession().getAttribute(Member.USER) == null) {
			logger.info(request.getRequestURI()+"돌아강");
			response.sendRedirect(contextPath+"/login");
			return false;
		}
		return true;
	}

}
