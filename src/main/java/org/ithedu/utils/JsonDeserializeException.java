package org.ithedu.utils;

public class JsonDeserializeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321731445272387083L;

	public JsonDeserializeException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	public JsonDeserializeException(String message) {
		super(message);
	}
	
	public JsonDeserializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
