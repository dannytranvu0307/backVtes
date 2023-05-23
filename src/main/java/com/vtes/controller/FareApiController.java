package com.vtes.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtes.exception.FareNotFoundException;
import com.vtes.model.FareDTO;
import com.vtes.model.ResponseData;
import com.vtes.model.ResponseData.ResponseType;
import com.vtes.service.FareService;

@RestController
@RequestMapping("/api/v1")
public class FareApiController {

	@Autowired
	private FareService fareService;

	@PostMapping("/fares")
	public ResponseEntity<?> saveFareRecord(@Valid @RequestBody FareDTO fareDTO, HttpServletRequest request) throws ParseException {

		try {
			//Get user id from request
			Integer userId = 1;
			fareDTO.setUserId(userId);
		} catch (Exception e) {

		}
		FareDTO savedFare = fareService.saveFareRecord(fareDTO);
		
		return ResponseEntity.ok()
				.body(ResponseData.builder()
						.code("200")
						.message("Fare detail saved")
						.type(ResponseType.INFO)
						.data(savedFare)
						.build()
						);
				
	}
	@DeleteMapping(value = "/fares", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteFareRecordById(
			@RequestParam(name = "recordId",required = true) Integer recordId) throws FareNotFoundException {
		if(!fareService.isExistFare(recordId))
			throw new FareNotFoundException("Fare ID is not found");
		
			fareService.deleteFareRecord(recordId);
		return ResponseEntity.ok()
				.body(ResponseData.builder()
					.code("200")
					.message("Deleted")
					.type(ResponseType.INFO)
					.build());
						

		}
	
	
}
