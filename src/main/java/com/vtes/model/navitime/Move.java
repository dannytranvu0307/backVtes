package com.vtes.model.navitime;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {
	@Getter(onMethod_ = { @JsonGetter("transitCount") })
	@Setter(onMethod_ = { @JsonSetter("transit_count") })
	private int transitCount;
	
	@Getter(onMethod_ = { @JsonGetter("fare") })
	@Setter(onMethod_ = { @JsonSetter("fare") })
	@JsonSerialize(using= FareSerializer.class)
	private Map<String, Integer> fares;
}
