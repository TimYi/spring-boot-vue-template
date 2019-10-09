/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ithedu.framework.web.ErrorCodes;
import org.ithedu.framework.web.ErrorResponse;
import org.ithedu.framework.web.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCodes.NOT_AUTHENTICATED, authException.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		WebUtils.writeJsonResponse(response, errorResponse.toJson());
	}

}
