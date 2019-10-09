/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.web;

public class RemoteInvokeException extends ApplicationException {

	private static final long serialVersionUID = 1322842500304579593L;

	public RemoteInvokeException(int status, String methodKey) {
		super("Remote invoke error, remote method is: " + methodKey, String.valueOf(status));
	}
}
