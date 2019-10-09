package org.ithedu.utils;

public class JsonSerializeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6871181085623287100L;

	public JsonSerializeException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	public JsonSerializeException(String message) {
		super(message);
	}
	
	public JsonSerializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
