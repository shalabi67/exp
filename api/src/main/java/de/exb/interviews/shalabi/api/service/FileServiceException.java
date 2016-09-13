package de.exb.interviews.shalabi.api.service;

import java.io.IOException;

public class FileServiceException extends IOException {

	private static final long serialVersionUID = 3689197784645132447L;

	public FileServiceException(final String aMessage) {
		super(aMessage);
	}

	public FileServiceException(final String aMessage, final Throwable aThrowable) {
		super(aMessage, aThrowable);
	}
	public FileServiceException(final String aMessage, int errorCode) {
		super(aMessage);
		this.errorCode = errorCode;
	}

	public FileServiceException(final String aMessage, int errorCode, final Throwable aThrowable) {
		super(aMessage, aThrowable);
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		String message = "Error Code:" + errorCode + " Message:" + super.getMessage();
		return message;
	}

	/**
	 * This is the exact error code that will be returned to client
     */
	int errorCode;
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
