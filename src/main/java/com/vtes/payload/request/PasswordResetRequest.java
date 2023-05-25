package com.vtes.payload.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
	private String newPassword;

	private String authToken;

}
