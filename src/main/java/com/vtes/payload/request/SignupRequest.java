package com.vtes.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignupRequest {
	@NotBlank
	@Size(min = 8, max = 128)
	private String fullName;

	@NotNull
	@Size(min = 1, max = 3)
	private int departmentId;

	@NotBlank
	@Size(min = 16, max = 128)
	@Pattern(regexp = ".+@vti\\.com\\.vn$")
	private String email;

	@NotBlank
	@Size(min = 8, max = 64)
	private String password;

}
