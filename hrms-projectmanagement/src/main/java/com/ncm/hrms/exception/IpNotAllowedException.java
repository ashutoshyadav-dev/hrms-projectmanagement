package com.ncm.hrms.exception;

public class IpNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IpNotAllowedException(String message) {
		super(message);
	}

}
