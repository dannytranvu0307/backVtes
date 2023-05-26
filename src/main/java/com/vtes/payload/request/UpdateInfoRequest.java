package com.vtes.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vtes.model.CommuterPassDTO;

import lombok.Data;

@Data
public class UpdateInfoRequest {
	@NotBlank
	private String fullName;

	@Size(min = 8, max = 64)
	private String password;

	@Size(min = 8, max = 64)
	private String newPassword;
	
	@NotNull
	@Size(min = 1, max = 3)
	private int departmentId;

	private CommuterPassDTO commuterPass;

}
