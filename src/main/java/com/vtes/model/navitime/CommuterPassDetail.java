package com.vtes.model.navitime;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
//@JsonSerialize(using = CommuterPassDetailSerializer.class)
public class CommuterPassDetail {
	private Integer no;
	private RouteSummaryNode start;
	private RouteSummaryNode goal;
	private Set<String> viaStations;
	private List<SubRoute> viaRoutes;
}
