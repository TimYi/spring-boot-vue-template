/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public HttpEntity<ErrorResponse> handleException(Exception e) {
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.create(e);
		return new HttpEntity<>(errorResponse);
	}
}
