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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCodes.NOT_AUTHORIZED, accessDeniedException.getMessage());
		WebUtils.writeJsonResponse(response, errorResponse.toJson());
	}

}
