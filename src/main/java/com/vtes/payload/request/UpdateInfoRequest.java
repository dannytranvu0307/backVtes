package com.vtes.payload.request;

import com.vtes.model.CommuterPassDTO;

import lombok.Data;

@Data
public class UpdateInfoRequest {

	private String fullName;

	private String password;

	private String newPassword;

	private int departmentId;
	
	private CommuterPassDTO commuterPass;

}
