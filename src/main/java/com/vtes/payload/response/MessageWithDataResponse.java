package com.vtes.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageWithDataResponse {

	private String message;

	private String type;

	private String code;

	private Object data;

}
