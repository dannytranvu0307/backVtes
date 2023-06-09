package com.vtes.exception;

public class AuthenticationFailedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	

	public AuthenticationFailedException(String msg) {
		super();
		this.msg = msg;
	}
	

	public AuthenticationFailedException() {
		super();
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
