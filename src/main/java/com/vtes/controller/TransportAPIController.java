package com.vtes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtes.exception.ParameterInvalidException;
import com.vtes.model.ResponseData;
import com.vtes.model.ResponseData.ResponseType;
import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.Station;
import com.vtes.repository.CommuterPassRepo;
import com.vtes.service.TransportInfomationServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class TransportAPIController {
	
	@Autowired
	private TransportInfomationServiceImpl transportService;
	
	@Autowired
	private CommuterPassRepo repo;
	
	@GetMapping("/routes")
	public ResponseEntity<?> getRouteDetails(
			@RequestParam(name="start",required = true) String start,
			@RequestParam(name = "goal",required = true) String goal,
			@RequestParam(name = "commuterPass", required = false) boolean active,
			HttpRequest request) 
			throws ParameterInvalidException{
		if(active) {
			//Xu li cua Truong hop dung ve thang 
		}
		List<Route> route = transportService.searchRoutes(start, goal);
		
		return ResponseEntity.ok().body(
				ResponseData.builder()
				.code("200")
				.message("")
				.type(ResponseType.INFO)
				.data(route)
				.build()
				);
		
	}
	@GetMapping("/stations")
	public ResponseEntity<?> getStationsByWord(
			@RequestParam(name = "word", required = true) String word) throws ParameterInvalidException{
		List<Station> stations = transportService.searchStationsByWord(word);
		return ResponseEntity.ok().body(
				ResponseData.builder()
				.code("200")
				.message("")
				.type(ResponseType.INFO)
				.data(stations)
				.build()
				);
	}
	
	@GetMapping("/cp-routes")
	public ResponseEntity<?> getCommuterPass(
			@RequestParam(name="start",required = true) String start,
			@RequestParam(name = "goal",required = true) String goal) 
			throws ParameterInvalidException{
		
		List<CommuterPassDetail> route = transportService.searchCommuterPassDetail(start, goal);
		
		return ResponseEntity.ok().body(
				ResponseData.builder()
				.code("200")
				.message("")
				.type(ResponseType.INFO)
				.data(route)
				.build()
				);
		
	}

}
