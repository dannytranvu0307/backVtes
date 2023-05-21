package com.vtes.model.navitime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkNodeItem {
	private String name;
	private String id;

}
