package com.reno.reno.model.exception;

public class ApiException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String propertyName;
	private String[] args;

	public ApiException(String errorCode, String propertyName) {
		this.errorCode = errorCode;
		this.propertyName = propertyName;
	}

	public ApiException(String errorCode, String propertyName, String[] args) {
		this.errorCode = errorCode;
		this.propertyName = propertyName;
		this.args = args;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
}
