package com.vtes.exception;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String msg;

	public BadRequestException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
