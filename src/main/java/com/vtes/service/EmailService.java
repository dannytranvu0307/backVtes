package com.vtes.service;

public interface EmailService {
	void sendRegistrationUserConfirm(String email, String token);
	
	void sendResetPasswordViaEmail(String email, String token);
}
