package com.vtes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtes.entity.CommuterPass;
import com.vtes.exception.ParameterInvalidException;
import com.vtes.model.ResponseData;
import com.vtes.model.ResponseData.ResponseType;
import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.Station;
import com.vtes.repository.CommuterPassNotFound;
import com.vtes.repository.CommuterPassRepo;
import com.vtes.service.TransportInfomationServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class TransportAPIController {
	private Logger LOGGER = LoggerFactory.getLogger(TransportAPIController.class);
	
	@Autowired
	private TransportInfomationServiceImpl transportService;
	
	@Autowired
	private CommuterPassRepo repo;
	
	@GetMapping("/routes")
	public ResponseEntity<?> getRouteDetails(
			@RequestParam(name="start",required = true) String start,
			@RequestParam(name = "goal",required = true) String goal,
			@RequestParam(name = "commuterPass", required = false) boolean cpActive,
			HttpServletRequest request) 
			throws ParameterInvalidException, CommuterPassNotFound{
		List<Route> routes = null;
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("goal", goal);
		
		if(cpActive) {
			/*Get user id from request
			 * Throw commuter pass is not exits exception
			*/
			Integer userId = 1;
			try {
				CommuterPass commuterPass = repo.findByUserId(userId);
				params.put("commuter_pass", commuterPass.getViaDetail());
				routes = transportService.searchRoutes(params);
			} catch (Exception e) {
				LOGGER.warn("Not found commuter pass with user id {}",userId);
				throw new CommuterPassNotFound("User ID "+userId+" of commuter pass is not exist");
			}
			
		}else {
			routes = transportService.searchRoutes(params);
		}
		
		return ResponseEntity.ok().body(
				ResponseData.builder()
				.code("200")
				.message("")
				.type(ResponseType.INFO)
				.data(routes)
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
		
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("goal",goal);
		
		List<CommuterPassDetail> cpRoutes = transportService.searchCommuterPassDetail(params);
		
		return ResponseEntity.ok().body(
				ResponseData.builder()
				.code("200")
				.message("")
				.type(ResponseType.INFO)
				.data(cpRoutes)
				.build()
				);
		
	}

}
