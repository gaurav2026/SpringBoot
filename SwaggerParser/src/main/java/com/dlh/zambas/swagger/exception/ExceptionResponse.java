package com.dlh.zambas.swagger.exception;

import org.springframework.http.HttpStatus;

/**
 * pojo class to return exception 
 * @author singhg
 *
 */
public class ExceptionResponse {
	private String errorMessage;
	private HttpStatus errorCode;

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(HttpStatus internalServerError) {
		this.errorCode = internalServerError;
	}

	public ExceptionResponse() {
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
