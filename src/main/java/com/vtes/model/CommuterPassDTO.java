package com.vtes.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommuterPassDTO {
	
	private String departure;
	private String destination;

	@JsonIgnore
	private List<String> via;
	
	@JsonIgnore
	private List<String> viaDetail;

}
