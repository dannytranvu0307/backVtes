package com.vtes.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vtes.entity.Fare;
import com.vtes.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareDTO {

	@JsonProperty("recordId")
	private Integer id;
	
	@JsonIgnore	
	private Integer userId;
	
	@NotBlank
	private String visitLocation;
	
	@NotBlank
	private String departure;
	
	@NotBlank
	private String destination;
	
	@NotNull
	private Integer payMethod;
	
	@NotNull
	private Boolean commuterPass;
	
	@NotNull
	private Boolean roundTrip;
	
	@NotNull
	private Integer fee;
	
	@NotNull
	private Integer transportation;
	
	@NotNull
	private String visitDate;
	
	private String createDate;
	
	public Fare convertToFare() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");	
		return Fare.builder()
				.id(id)
				.user(new User(userId))
				.departure(departure)
				.visitLocation(visitLocation)
				.destination(destination)
				.fee(fee)
				.payMethod(payMethod)
				.useCommuterPass(commuterPass)
				.isRoundTrip(roundTrip)
				.transportation(transportation)
				.visitDate(dateFormat.parse(visitDate))
				.build();
	}
	
}
