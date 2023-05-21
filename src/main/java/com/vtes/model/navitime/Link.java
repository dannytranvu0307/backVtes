package com.vtes.model.navitime;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
	private String id;
	private String name;
	private String direction;
	private LinkNodeItem from;
	private LinkNodeItem to;
	
	
	//Use to generate via commuter pass link
	public String generateViaJson() {
		Map<String, String> link = new HashMap<>();
		link.put("start", from.getId());
		link.put("goal", to.getId());
		link.put("direction", direction);
		link.put("link", id);

		try {
			String json = new ObjectMapper().writeValueAsString(link);
			return json;
		} catch (JsonProcessingException e) {
		}
		return null;
	}

}
