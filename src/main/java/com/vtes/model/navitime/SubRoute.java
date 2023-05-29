package com.vtes.model.navitime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SubRoute {
	private String lineSymbol;
	private String lineColor;
	private String lineName;
	private List<String> links;
}
