/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.web;

import java.util.List;

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6311863420968951838L;
	private String code;
	private List<String> oldStackTraces;

	public String getCode() {
		return code;
	}

	public ApplicationException() {

	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, String code) {
		super(message);
		this.code = code;
	}

	public ApplicationException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String code, String message, List<String> oldStackTraces) {
		super(message);
		this.code = code;
		this.oldStackTraces = oldStackTraces;
	}

	public List<String> getOldStackTraces() {
		return oldStackTraces;
	}

	public void setOldStackTraces(List<String> oldStackTraces) {
		this.oldStackTraces = oldStackTraces;
	}
}
