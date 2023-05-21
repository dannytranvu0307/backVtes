package com.vtes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData {	
	public enum ResponseType {
		INFO,WARINING,ERROR;
	}
	private ResponseType type;
	private String message;
	private String code;
	private Object data;
	

}

