/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 28, 2015 4:47:45 PM 
 * 
 */
package com.epweike.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author wuxp
 *
 */
public class MyAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String message = exception.getMessage();

		response.getWriter().println(
				"{\"failure\":true,\"message\":\"" + message + "\"}");
	}

}
