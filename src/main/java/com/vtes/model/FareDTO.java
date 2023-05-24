package com.vtes.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
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
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date visitDate;

	@JsonIgnore
	private String createDate;

	public Fare convertToFare() throws ParseException {
		return Fare.builder().id(id).user(new User(userId)).departure(departure).visitLocation(visitLocation)
				.destination(destination).fee(fee).payMethod(payMethod).useCommuterPass(commuterPass)
				.isRoundTrip(roundTrip).transportation(transportation).visitDate(visitDate).build();
	}

}
