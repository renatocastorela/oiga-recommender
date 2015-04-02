package org.oiga.exceptions;

public class NullEMailException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullEMailException() {
		super();
	}

	public NullEMailException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NullEMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullEMailException(String message) {
		super(message);
	}

	public NullEMailException(Throwable cause) {
		super(cause);
	}
	

}
