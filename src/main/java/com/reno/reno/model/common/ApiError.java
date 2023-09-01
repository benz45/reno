package com.reno.reno.model.common;

import lombok.Data;

@Data
public class ApiError {
	private String statusCode;
	private String errorCode;
	private String errorMessage;
	private String project;

	public ApiError() {
	}

	public ApiError(String statusCode, String errorCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.project = "Reno";
	}
}
