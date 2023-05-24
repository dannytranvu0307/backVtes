package com.vtes.model;

import java.text.ParseException;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Boolean isRoundTrip;

	@NotNull
	private Integer fee;

	@NotNull
	private String transportation;

	@NotNull
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date visitDate;

	public Fare convertToFare() throws ParseException {
		return Fare.builder().id(id).user(new User(userId)).departure(departure).visitLocation(visitLocation)
				.destination(destination).fee(fee).payMethod(payMethod).useCommuterPass(commuterPass)
				.isRoundTrip(isRoundTrip).transportation(transportation).visitDate(visitDate).build();
	}

}
