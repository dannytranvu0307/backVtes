package com.vtes.model.navitime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class RouteSummaryNode {
	private String name;
	
	@Getter(onMethod_ = {@JsonGetter("nodeId")})
	@Setter(onMethod_ = {@JsonSetter("node_id")})
	private String nodeId;
	
	@JsonIgnore
	@Setter(onMethod_ = {@JsonSetter("node_types")})
	private List<String> nodeTypes;
	
}
