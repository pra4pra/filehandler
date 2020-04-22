package com.prathap.file.filehandler.common;

public class FilehandlerRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874571868542827390L;

	public FilehandlerRuntimeException() {
		super();
	}

	public FilehandlerRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FilehandlerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilehandlerRuntimeException(String message) {
		super(message);
	}

	public FilehandlerRuntimeException(Throwable cause) {
		super(cause);
	}
	
	

}
