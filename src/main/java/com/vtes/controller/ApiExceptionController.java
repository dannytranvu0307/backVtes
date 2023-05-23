package com.vtes.controller;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.vtes.exception.BadRequestException;
import com.vtes.exception.CommuterPassNotFound;
import com.vtes.exception.FareNotFoundException;
import com.vtes.exception.ParameterInvalidException;
import com.vtes.model.ResponseData;
import com.vtes.model.ResponseData.ResponseType;

@RestControllerAdvice
public class ApiExceptionController {
	private Logger LOGGER = LoggerFactory.getLogger(ApiExceptionController.class);

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData badRequestResponse(Exception ex) {
		return ResponseData.builder()
				.code("400")
				.message("Bad Request")
				.type(ResponseType.ERROR)
				.build();
		
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData methodArgumentTypeMissmatch(Exception ex) {
		return ResponseData.builder()
				.code("400")
				.message(ex.getMessage())
				.type(ResponseType.ERROR)
				.build();
		
	}
	
	@ExceptionHandler(ParameterInvalidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData invalidParamExResponse(Exception ex) {
		return ResponseData.builder()
				.code("API_ER02")
				.message("Invalid parameter")
				.type(ResponseType.ERROR)
				.build();
		
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData missingRequestParameterResponse(Exception ex) {
		return ResponseData.builder()
				.code("API_ER02")
				.message(ex.getMessage())
				.type(ResponseType.ERROR)
				.build();
		
	}
	@ExceptionHandler(FareNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData fareNotFoundException(FareNotFoundException ex) {
		return ResponseData.builder()
				.code("API010_ER")
				.message("Not found fare record")
				.type(ResponseType.WARINING)
				.build();
		
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData maxUploadSizeExceoption(Exception ex) {
		return ResponseData.builder()
				.code("API006_ER")
				.message("This file size is too large")
				.type(ResponseType.ERROR)
				.build();
		
	}
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData notFoundResource(EntityNotFoundException ex) {
		return ResponseData.builder()
				.code("404")
				.message(ex.getMessage())
				.type(ResponseType.ERROR)
				.build();
		
	}
	@ExceptionHandler(CommuterPassNotFound.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseData notFoundCommuterPass(Exception ex) {
		return ResponseData.builder()
				.code("API008_ER")
				.message(ex.getMessage())
				.type(ResponseType.ERROR)
				.build();
		
	}
	
}
