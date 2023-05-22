package com.vtes.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdateInfoRequest {

	private String fullName;

	private String password;

	private String newPassword;

	private int departmentId;

}
