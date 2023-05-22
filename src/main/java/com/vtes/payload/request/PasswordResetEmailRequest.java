package com.vtes.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordResetEmailRequest {
	@NotBlank
	private String email;

}
