package com.ncm.hrms.exception;

public class ErrorResponse {

	private String message;
	private Integer status;
	private Long timestamp;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String message, Integer status) {
		super();
		this.message = message;
		this.status = status;
	}

	public ErrorResponse(String message, Integer status, Long timestamp) {
		super();
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Integer getStatus() {
		return status;
	}

	public Long getTimestamp() {
		return timestamp;
	}

}
