package com.vtes.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommuterPassDTO {
	private String departure;
	private String destination;
	private List<String> via;
	private List<String> viaDetail;

}
