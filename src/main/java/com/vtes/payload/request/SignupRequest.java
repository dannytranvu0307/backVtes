package com.vtes.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignupRequest {
	@NotBlank
//    @Size(min = 3, max = 20)
	private String fullName;

	@NotNull
	private int departmentId;

	@NotBlank
//    @Size(max = 50)
//	@Email
	private String email;

	@NotBlank
//    @Size(min = 6, max = 40)
	private String password;

}
